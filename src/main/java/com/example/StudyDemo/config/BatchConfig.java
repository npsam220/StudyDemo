package com.example.StudyDemo.config;

import com.example.StudyDemo.entity.Product;
import jakarta.persistence.EntityManagerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * 🔧 バッチ設定（JPAリーダーの定義）
 * - JpaPagingItemReaderを使用して、Productエンティティをページングで読み取る設定
 * - これにより、大量のデータを効率的に処理できるようになります
 * - 例えば、exportCsvBatch()で全商品をCSV出力する際に、このリーダーを使用してデータを取得することができます
 * 
 * ＠param emf
 * ＠return
 **/
@Configuration
public class BatchConfig {
    @Bean
    @StepScope
    public JpaPagingItemReader<Product> reader(
            EntityManagerFactory emf,
            @Value("#{jobParameters['id']}") String id,
            @Value("#{jobParameters['productCode']}") String productCode,
            @Value("#{jobParameters['name']}") String name,
            @Value("#{jobParameters['pricebegin']}") String pricebegin,
            @Value("#{jobParameters['priceend']}") String priceend) {

        JpaPagingItemReader<Product> reader = new JpaPagingItemReader<>();
        reader.setName("productReader");
        reader.setEntityManagerFactory(emf);

        StringBuilder query = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        Map<String, Object> parameters = new HashMap<>();

        if (id != null && !id.isBlank()) {
            query.append(" AND p.id = :id");
            parameters.put("id", Long.valueOf(id));
        }

        if (productCode != null && !productCode.isBlank()) {
            query.append(" AND p.productCode = :productCode");
            parameters.put("productCode", productCode);
        }

        if (name != null && !name.isBlank()) {
            query.append(" AND p.name LIKE :name");
            parameters.put("name", "%" + name + "%");
        }

        if (pricebegin != null && !pricebegin.isBlank()) {
            query.append(" AND p.price >= :pricebegin");
            parameters.put("pricebegin", Integer.valueOf(pricebegin));
        }

        if (priceend != null && !priceend.isBlank()) {
            query.append(" AND p.price <= :priceend");
            parameters.put("priceend", Integer.valueOf(priceend));
        }

        reader.setQueryString(query.toString());
        reader.setParameterValues(parameters);
        reader.setPageSize(10); // chunk size
        return reader;
    }

    /*
     * 🔧 バッチ設定（プロセッサーとライターの定義）
     * - ItemProcessorは、読み取ったデータを必要に応じて変換するためのインターフェース
     * - ここでは、単純に読み取ったProductをそのまま返す実装としていますが、
     * 必要に応じて価格のフォーマット変更や在庫数の計算などの処理を追加することも可能です
     * - FlatFileItemWriterは、処理されたデータをCSVファイルに書き出すためのクラスで、ヘッダーの設定やデータのフォーマットを行っています
     */
    @Bean
    @StepScope
    public ItemProcessor<Product, Product> processor() {
        return item -> item; // 直接回傳
        // return item -> {
        // System.out.println(
        // "*****Processing product: " + item.getId() + ", " + item.getName() + ", " +
        // item.getPrice());
        // // ⭐ 庫存低於 20，自動補貨
        // if (item.getStock() < 20) {

        // item.setStock(item.getStock() + 50);

        // System.out.println(
        // "補貨商品: " +
        // item.getName() +
        // " → 新庫存: " +
        // item.getStock());
        // throw new RuntimeException(
        // "故意測試 rollback");
        // }

        // return item;
        // };
    }

    /**
     * 🔧 バッチ設定（フラットファイルライターの定義）
     * - FlatFileItemWriterを使用して、処理されたProductデータをCSVファイルに書き出す設定
     * - ファイル名にはタイムスタンプを付加して、毎回異なるファイルが生成されるようにしています
     * - ヘッダーには、CSVの列名を設定し、データのフォーマットは、Productの各フィールドをカンマ区切りで出力するようにしています
     */
    @Bean
    @StepScope
    public FlatFileItemWriter<Product> writer() {

        FlatFileItemWriter<Product> writer = new FlatFileItemWriter<>();
        String path = "download_files/";
        new File(path).mkdirs();
        String filename = path + "products_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) +
                ".csv";

        writer.setResource(new FileSystemResource(filename));

        // header
        writer.setHeaderCallback(w -> w.write("ID,商品コード,商品名,価格,在庫数"));

        // data mapping
        writer.setLineAggregator(item -> item.getId() + "," +
                item.getProductCode() + "," +
                item.getName() + "," +
                item.getPrice() + "," +
                item.getStock());

        return writer;
    }

    /**
     * 🔧 バッチ設定（ステップの定義）
     * - Stepは、バッチジョブの基本的な構成要素で、リーダー、プロセッサー、ライターを組み合わせて定義します
     * - ここでは、chunk-oriented processingを使用して、10件ずつデータを処理するように設定しています
     * -
     * JobRepositoryとPlatformTransactionManagerは、バッチの実行管理とトランザクション管理のために必要なコンポーネントで、Spring
     * Batchが提供
     * 
     * @param jobRepository
     * @param transactionManager
     * @param reader
     * @param processor
     * @param writer
     * @return
     */
    @Bean
    public Step step(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JpaPagingItemReader<Product> reader,
            ItemProcessor<Product, Product> processor,
            FlatFileItemWriter<Product> writer
    // JpaItemWriter<Product> writer
    ) {

        return new StepBuilder("productStep", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /**
     * 🔧 バッチ設定（ジョブの定義 ）
     * -
     * Jobは、複数のステップを組み合わせて定義することができるバッチジョブの基本的な構成要素で、ここでは単一のステップを実行するシンプルなジョブを定義しています
     * 
     * @param jobRepository
     * @param step
     * @return
     */
    @Bean
    public Job productJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("productJob", jobRepository)
                .start(step)
                .build();
    }

    /**
     * 🔧 バッチ設定（JPAライターの定義）
     * - JpaItemWriterを使用して、処理されたProductデータをデータベースに書き込む設定
     * - EntityManagerFactoryを使用して、JPAのエンティティマネージャーを取得し、データベースへの
     * 
     * @param emf
     * @return
     */
    @Bean
    public JpaItemWriter<Product> dbWriter(EntityManagerFactory emf) {

        JpaItemWriter<Product> writer = new JpaItemWriter<>();

        writer.setEntityManagerFactory(emf);

        return writer;
    }
}
