package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardDAO {
	public void create(BoardVO vo) throws Exception;

	public BoardVO read(Integer bno) throws Exception;

	public void update(BoardVO vo) throws Exception;

	public void delete(Integer bno) throws Exception;

	public List<BoardVO> listAll() throws Exception;

	public List<BoardVO> listPage(int page) throws Exception;

	public List<BoardVO> listCriteria(Criteria cri) throws Exception;

	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;

	public int totalCount() throws Exception;

	public int searchCount(SearchCriteria cri) throws Exception;

	public void updateReplyCnt(int bno, int amount) throws Exception;
	
	
	// 파일 처리
	public void addAttach(String fullname)  throws Exception;
	public void addAttachByBno(String fullname, int bno) throws Exception;
	public List<String> selectAttach(int bno) throws Exception;
	public void deleteAttach(String fullname) throws Exception;
	public void deleteAttachByBno(int bno) throws Exception;
}
