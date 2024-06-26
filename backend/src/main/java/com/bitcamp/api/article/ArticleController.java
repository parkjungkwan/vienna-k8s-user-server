package com.bitcamp.api.article;

import java.sql.SQLException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bitcamp.api.article.model.ArticleDto;
import com.bitcamp.api.article.service.ArticleServiceImpl;
import com.bitcamp.api.common.component.Messenger;
import com.bitcamp.api.common.component.pagination.PageRequestVo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
    @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping("/api/articles")
@Log4j2
public class ArticleController {

    private final ArticleServiceImpl service;


    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody ArticleDto dto) throws SQLException {
        log.info("게시글 작성 정보: "+dto.toString());
        return ResponseEntity.ok(service.save(dto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam long id) throws SQLException {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<ArticleDto>> findByBoardId(PageRequestVo vo) throws SQLException {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/detail")
    public ResponseEntity<Messenger> findById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @GetMapping("/count")
    public ResponseEntity<Messenger> count() throws SQLException {
        return ResponseEntity.ok(service.count());
    }
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(@RequestParam long id) throws SQLException {
        return ResponseEntity.ok(service.existsById(id));
    }
}
