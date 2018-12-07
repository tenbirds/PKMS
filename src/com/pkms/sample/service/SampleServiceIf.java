package com.pkms.sample.service;

import java.util.List;

import com.pkms.sample.model.SampleModel;

public interface SampleServiceIf {

	public void create(SampleModel sampleModel) throws Exception;

	public SampleModel read(SampleModel sampleModel) throws Exception;

	public List<?> readList(SampleModel sampleModel) throws Exception;

	public void update(SampleModel sampleModel) throws Exception;

	public void delete(SampleModel sampleModel) throws Exception;


}
