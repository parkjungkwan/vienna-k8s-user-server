package com.bitcamp.api.board.model;

import java.util.List;
import jakarta.persistence.*;

import com.bitcamp.api.article.model.Article;
import com.bitcamp.api.common.model.BaseEntity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "boards")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;
    private String title;
    private String description;
    private String content;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @Builder(builderMethodName = "builder")
    public Board(long boardId, String title, String description) {
        this.boardId = boardId;
        this.title = title;
        this.description = description;
    }


}
