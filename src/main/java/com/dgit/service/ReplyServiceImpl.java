package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.BoardDAO;
import com.dgit.persistence.ReplyDAO;


@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	private ReplyDAO dao;
	
	@Autowired
	BoardDAO boardDao;

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.list(bno);
	}

	
	@Override
	@Transactional
	public void addReply(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.create(vo);
		boardDao.updateReplyCnt(vo.getBno(), 1);
		
		// 기존이라면 dao 작업시 커넥션 1번 열리고, boardDao 작업을 위해 커넥션 2번째가 열림... 2번 열린다.
		// 한번 커넥션에서 되도록 하기 위해서 하는 작업~!!! 
		// <<1>> p.459 pom.xml 에서 라이브러리 4개 추가[spring-aop/spring-tx/aspectjrt/aspectjtools]
		// <<2>> root-context에서  tx 체크해줌
		// <<3>> root-context 소스에서  추가해줌
		/*<bean id="transactionManager"
				class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
				<property name="dataSource" ref="dataSource"></property>
			</bean>
			<tx:annotation-driven/>
		*/
		// <<4>> @Transactional 달아주긔~!
		
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	public void removeReply(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		int bno = dao.getBno(vo.getRno()); // <<1>>
		dao.delete(vo); // <<2>>
		// <<1>> <<2>> 순서 지키세용... 먼저 지우고 찾으면 찾을수가 없어~!!!
		
		boardDao.updateReplyCnt(bno, -1);
	}

	@Override
	public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listPage(bno, cri);
	}

	@Override
	public int count(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.count(bno);
	}

}
