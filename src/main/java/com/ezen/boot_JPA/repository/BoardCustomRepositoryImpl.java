package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ezen.boot_JPA.entity.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    // 실제 구현
    @Override
    public Page<Board> serachBoards(String type, String keyword, Pageable pageable) {
        // 조건이 많을 경우
        // select * from baord where
        // isDel = 'n' and title like '% aaa %'
        // BooleanExpression condition = board.isDel.eq('N');
        BooleanExpression condition = null;

        // 동적 검색 조건 추가
        if(type != null && keyword != null){
            String[] typearr = type.split("");
            // 이전 선행 조건이 있다면...
            BooleanExpression dynamicCondition = null;
            for(String t : typearr){
                switch (t) {
                    case "t":
                        dynamicCondition = (dynamicCondition == null) ?
                                board.title.containsIgnoreCase(keyword) : dynamicCondition.or(board.title.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        dynamicCondition = (dynamicCondition == null) ?
                                board.writer.containsIgnoreCase(keyword) : dynamicCondition.or(board.writer.containsIgnoreCase(keyword));
                        ;
                        break;
                    case "c":
                        dynamicCondition = (dynamicCondition == null) ?
                                board.content.containsIgnoreCase(keyword) : dynamicCondition.or(board.content.containsIgnoreCase(keyword));
                        break;
                }
            }
            if(dynamicCondition != null){
//                condition = condition.and(dynamicCondition);
                condition = dynamicCondition;
            }
/*            switch (type){
                case "t" :
                    condition = board.title.containsIgnoreCase(keyword);
                    break;
                case "w" :
                    condition = board.writer.containsIgnoreCase(keyword);
                    break;
                case "c" :
                    condition = board.content.containsIgnoreCase(keyword);
                    break;
                case "tw" :
                    condition = board.title.containsIgnoreCase(keyword).or(board.writer.containsIgnoreCase(keyword));
                    break;
                case "wc" :
                    condition = board.writer.containsIgnoreCase(keyword).or(board.content.containsIgnoreCase(keyword));
                    break;
                case "tc" :
                    condition = board.title.containsIgnoreCase(keyword).or(board.content.containsIgnoreCase(keyword));
                    break;
            }*/
        }
        // 쿼리 작성 및 페이징 적용
        List<Board> result = queryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.bno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // 검색된 데이터의 전체 개수 조합
        long total = queryFactory
                .selectFrom(board)
                .where(condition)
                .fetch().size();     // .fetchCount()

        return new PageImpl<>(result, pageable, total);
    }
}
