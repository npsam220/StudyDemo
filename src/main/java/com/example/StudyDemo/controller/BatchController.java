package com.example.StudyDemo.controller;

import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
    /*
     * BatchControllerは、Spring Batchのジョブを起動するためのコントローラークラスです。
     * - JobLauncherは、Spring Batchのジョブを実行するためのインターフェースで、ジョブの起動や管理を行います
     * - Jobは、Spring Batchのジョブを表すインターフェースで、実行するバッチ処理の定義を持っています
     * Spring BootのAutoConfigurationにより、
     * JobLauncherなどのBatch関連Beanは自動的に生成されます。
     * そのため、明示的にBean定義をしなくてもDIが可能です。
     */
    private final JobLauncher jobLauncher;
    private final Job productJob;

    public BatchController(JobLauncher jobLauncher, Job productJob) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
    }

    /*
     * 🔧 バッチ起動エンドポイントの定義
     * - /batch/runにアクセスすると、JobLauncherを使用してproductJobを起動するエンドポイントを定義しています
     * - JobParametersBuilderを使用して、ジョブに渡すパラメータを作成しています。ここでは、現在の時間をパラメータとして渡すことで、
     * ジョブの一意性を確保しています（同じパラメータでジョ
     */
    @GetMapping("/batch/run")
    public String runBatch(
            @Parameter(description = "商品ID") @RequestParam(required = false) Long id,
            @Parameter(description = "商品コード") @RequestParam(required = false) String productCode,
            @Parameter(description = "商品名") @RequestParam(required = false) String name,
            @Parameter(description = "価格範囲（開始）") @RequestParam(required = false) Integer pricebegin,
            @Parameter(description = "価格範囲（終了）") @RequestParam(required = false) Integer priceend) {
        try {
            JobParametersBuilder builder = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()); // ⭐ 每次不同

            if (id != null) {
                builder.addString("id", id.toString());
            }
            if (productCode != null && !productCode.isBlank()) {
                builder.addString("productCode", productCode);
            }
            if (name != null && !name.isBlank()) {
                builder.addString("name", name);
            }
            if (pricebegin != null) {
                builder.addString("pricebegin", pricebegin.toString());
            }
            if (priceend != null) {
                builder.addString("priceend", priceend.toString());
            }

            JobParameters params = builder.toJobParameters();

            jobLauncher.run(productJob, params);

            return "Batch started!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Batch failed: " + e.getMessage();
        }
    }
}
