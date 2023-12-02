package com.example.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BoardDAO {
    private final String BOARD_INSERT = "insert into BOARD (title, writer, content, category, editdate) values (?,?,?,?,?)";
    private final String BOARD_UPDATE = "update BOARD set title=?, writer=?, content=?, category=?, editdate=? where seq=?";
    private final String BOARD_DELETE = "delete from BOARD  where seq=?";
    private final String BOARD_GET = "select * from BOARD  where seq=?";
//    private final String BOARD_LIST = "select * from BOARD order by seq desc";
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int insertBoard(BoardVO vo) {
        return jdbcTemplate.update(BOARD_INSERT,
                new Object[]{vo.getTitle(), vo.getWriter(), vo.getContent(), vo.getCategory(), vo.getEditdate()});

    }

    public int deleteBoard(int id){
        return jdbcTemplate.update(BOARD_DELETE,new Object[]{id});
    }

    public int updateBoard(BoardVO vo){
        return jdbcTemplate.update(BOARD_UPDATE,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent(), vo.getSeq()});
    }

    public BoardVO getBoard(int seq){
        return jdbcTemplate.queryForObject(BOARD_GET,new Object[]{seq},
                new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
    }


    class BoardRowMapper implements RowMapper<BoardVO> {
        @Override
        public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BoardVO one = new BoardVO();
            one.setSeq(rs.getInt("seq"));
            one.setTitle(rs.getString("title"));
            one.setWriter(rs.getString("writer"));
            one.setContent(rs.getString("content"));
            one.setRegdate(rs.getDate("regdate"));
            one.setCnt(rs.getInt("cnt"));
            one.setCategory(rs.getString("category"));
            one.setEditdate(rs.getDate("editdate"));
            return one;
        }
    }
//    public List<BoardVO> getBoardList(){
//        String sql = "select * form BOARD order by regdate desc";
//        List<BoardVO> list = jdbcTemplate.query(sql, new RowMapper<BoardVO>() {
//            @Override
//            public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException{
//                BoardVO one = new BoardVO();
//                one.setSeq(rs.getInt("seq"));
//                one.setTitle(rs.getString("title"));
//                one.setWriter(rs.getString("writer"));
//                one.setContent(rs.getString("content"));
//                one.setRegdate(rs.getDate("regdate"));
//                one.setCnt(rs.getInt("cnt"));
//                one.setCategory(rs.getString("category"));
//                one.setEditdate(rs.getDate("editdate"));
//                return one;
//            }
//        });
//
//        return list;
//}
public List<BoardVO> getBoardList(){
    String sql = "select * from BOARD order by regdate desc";
    return jdbcTemplate.query(sql,new BoardRowMapper());
}

}

