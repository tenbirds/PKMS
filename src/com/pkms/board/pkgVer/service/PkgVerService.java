package com.pkms.board.pkgVer.service;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.pkgVer.dao.PkgVerDao;
import com.pkms.board.pkgVer.model.PkgVerModel;


@Service("PkgVerService")
public class PkgVerService implements PkgVerServiceIf{
	@Resource(name = "PkgVerDao")
	private PkgVerDao pkgVerDao;

	@Override
	public TreeMap<String, ArrayList<PkgVerModel>> readList(PkgVerModel pkgVerModel) throws Exception {
			
		 TreeMap<String, ArrayList<PkgVerModel>> kindMapList = new TreeMap<String, ArrayList<PkgVerModel>>();
		
		  List<PkgVerModel> pkgVerList = pkgVerDao.readList(pkgVerModel);
		 
		  int totalCount = 0;
		  totalCount = pkgVerDao.readTotalCount(pkgVerModel);
		
		  for(PkgVerModel pkgVer : pkgVerList){
		   
		   String kindKey = pkgVer.getGroup_name();
		 
		   ArrayList<PkgVerModel> kindList = kindMapList.get(kindKey);
		   if (kindList == null) {
			   kindList = new ArrayList<PkgVerModel>();
			   }
		   kindList.add(pkgVer);
		   kindMapList.put(kindKey, kindList);
		  
		  }
		
		  pkgVerModel.setTotalCount(totalCount);
		return kindMapList;
	}
	
	@Override
	public PkgVerModel read(PkgVerModel pkgVerModel) throws Exception{
		pkgVerModel = pkgVerDao.read(pkgVerModel);
		return pkgVerModel;
	}
	
	@Override
	public void create(PkgVerModel pkgVerModel) throws Exception{
		PkgVerModel readPkgVerModel = new PkgVerModel();
		readPkgVerModel.setPageGubun("Y");
		 
		  List<PkgVerModel> pkgVerList = pkgVerDao.readList(readPkgVerModel);
			
		  int cnt = 0;
		  for(int i=0; i<pkgVerList.size(); i++ ){
			 
			if(pkgVerList.get(i).getSystem_seq().equals(pkgVerModel.getSystem_seq())){
				cnt++;
			}
		  }
		
		  if(cnt==1){
			  this.pkgVerDao.update(pkgVerModel);
		  } else {
			  this.pkgVerDao.create(pkgVerModel);
		  }
	}
	
	@Override
	public void update(PkgVerModel pkgVerModel) throws Exception{
		this.pkgVerDao.update(pkgVerModel);
	}
	
	@Override
	public void delete(PkgVerModel pkgVerModel) throws Exception{
		this.pkgVerDao.delete(pkgVerModel);
	}
	
	
}
