package com.example.StudyDemo.controller;

import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.exception.ProductNotFoundException;
import com.example.StudyDemo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "在庫管理")  
public class ProductController {
    ProductService service;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public ProductController(ProductService service) {
        this.service = service;
    }
      // 搜尋
    @Operation (summary = "商品を検索")
    @GetMapping("/search")
    @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "検索成功",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Product.class)
        )
    ),
    @ApiResponse(responseCode = "400", description = "パラメータ不正"),
    @ApiResponse(responseCode = "500", description = "システムエラー")
   })
    public List<Product> search(
        @Parameter(description = "商品ID")
        @RequestParam(required = false) Long id,
        @Parameter(description = "商品コード")
        @RequestParam(required = false) String productCode,
        @Parameter(description = "商品名")
        @RequestParam(required = false) String name,
        @Parameter(description = "価格範囲（開始）")
        @RequestParam(required = false) Integer pricebegin,
        @Parameter(description = "価格範囲（終了）")
        @RequestParam(required = false) Integer priceend
       ) {
        log.info("product search - id: {}, productCode: {}, name: {}, pricebegin: {}, priceend: {}", id, productCode, name, pricebegin, priceend);
     
        List<Product> result = service.search(
            id,
            productCode,
            name,
            pricebegin,
            priceend
        );
       /* for (Product p : result) {
            System.out.println("Found product: " + p.getId() + ", " + p.getName() + ", " + p.getPrice());
        }*/ 
        return result;
    }
     // 更新
    @PutMapping("/{id}")
    @Operation(summary = "商品を更新")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
         Product existing = service.findById(id);
         log.info("product search - id: {}", id);
        if (existing == null) {
           throw new ProductNotFoundException("商品が存在しません");
        }
        existing.setProductCode(product.getProductCode());
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

       
        return service.save(existing);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "商品を削除")
    public void delete(@PathVariable Long id) {
        Product existing = service.findById(id);
        if (existing == null) {
           throw new ProductNotFoundException("商品が存在しません");  
        }
        service.deleteById(id);
    }
    @PostMapping("/create")
    @Operation(summary = "商品を作成")
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }
    
    @GetMapping("/csv")
    public void exportProductCsv(
        HttpServletResponse response,
        @Parameter(description = "商品ID")
        @RequestParam(required = false) Long id,
        @Parameter(description = "商品コード")
        @RequestParam(required = false) String productCode,
        @Parameter(description = "商品名")
        @RequestParam(required = false) String name,
        @Parameter(description = "価格範囲（開始）")
        @RequestParam(required = false) Integer pricebegin,
        @Parameter(description = "価格範囲（終了）")
        @RequestParam(required = false) Integer priceend
    ) throws IOException {
       
        response.setContentType("text/csv; charset=UTF-8");
        String filename = "products_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        
         List<Product> list = service.search(id, productCode, name, pricebegin, priceend);
        // System.out.println("list.size(): " + list.size());
         log.info("exportProductCsv - id: {}, productCode: {}, name: {}, pricebegin: {}, priceend: {}, result count: {}", id, productCode, name, pricebegin, priceend, list.size());
         PrintWriter writer = response.getWriter();

         // ⭐ 日本公司會用日文欄位
         writer.write("\uFEFF"); // 防亂碼（Excel用）
         writer.println("ID,商品コード,商品名,価格,在庫数");

        for (Product p : list) {
        writer.println(
           csv(p.getId()) + "," +
           csv(p.getProductCode()) + "," +
           csv(p.getName()) + "," +
           csv(p.getPrice()) + "," +
           csv(p.getStock())
        );
    }

        writer.flush();
   }
 

   // 🔥 防逗號、換行
   private String csv(Object value) {
      if (value == null) return "";

      String str = value.toString();
      return "\"" + str.replace("\"", "\"\"") + "\"";
   }
}
