<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<c:set var="registerFlag" value="${empty PkgModel.pkg_seq ? '등록' : '수정'}" />
<body>
		<!--기본정보 -->
		<input type="hidden" id="master_file_id" name="master_file_id" value="${PkgModel.master_file_id}" />
		<input type="hidden" id="deleteList" name="deleteList" value="${PkgModel.deleteList}" />
		<input type="hidden" id="col2" name="col2" value="${PkgModel.col2}" />
		<input type="hidden" id="col3" name="col3" value="${PkgModel.col3}" />
		<input type="hidden" id="col5" name="col5" value="${PkgModel.col5}" />
		<input type="hidden" id="col7" name="col7" value="${PkgModel.col7}" />
		<input type="hidden" id="col9" name="col9" value="${PkgModel.col9}" />
		<input type="hidden" id="col11" name="col11" value="${PkgModel.col11}" />
		<input type="hidden" id="col13" name="col13" value="${PkgModel.col13}" />
		<input type="hidden" id="col15" name="col15" value="${PkgModel.col15}" />
		<input type="hidden" id="col17" name="col17" value="${PkgModel.col17}" />
		<input type="hidden" id="col19" name="col19" value="${PkgModel.col19}" />
		<input type="hidden" id="col21" name="col21" value="${PkgModel.col21}" />
		<input type="hidden" id="col23" name="col23" value="${PkgModel.col23}" />
		<input type="hidden" id="col23" name="col23" value="${PkgModel.col23}" />
		
		<input type="hidden" id="col33" name="col33" value="${PkgModel.col33}" />
		<input type="hidden" id="col34" name="col34" value="${PkgModel.col34}" />
		<input type="hidden" id="col35" name="col35" value="${PkgModel.col35}" />
		<input type="hidden" id="col36" name="col36" value="${PkgModel.col36}" />
		<input type="hidden" id="col37" name="col37" value="${PkgModel.col37}" />
		<input type="hidden" id="col38" name="col38" value="${PkgModel.col38}" />
		<input type="hidden" id="col40" name="col40" value="${PkgModel.col40}" />
		<input type="hidden" id="col42" name="col42" value="${PkgModel.col42}" />

		
		<ul class="acc" id="acc">
						<li>
							<h3 class="">공급사 자체 검증 <a class="btn-close" href="javascript:div_open_close('1')" id="oc_1">닫기</a></h3>
							<div id="acc_1" class="acc-section">
							
									<table class="pop_tbl_type1" style="width:744px;" cellspacing="0">
										<colgroup>
											<col width="200" />
											<col width="*" />
										</colgroup>
										<tbody>
										<tr>
											<th scope="col">보완내역별 시험 결과 <span class='necessariness'>*</span></th>
											<td>
												<input type="text" id="col4" name="col4" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">Regression Test 및<br/>기본 검증 결과 <span class='necessariness'>*</span></th>
											<td>
												<input type="text" id="col6" name="col6" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">성능 용량 시험 결과</th>
											<td>
												<input type="text" id="col8" name="col8" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										</tbody>
									</table>
							</div>
						</li>
						
						<li>
							<h3 class="mag_top1">개발 검증 <a class="btn-close" href="javascript:div_open_close('2')" id="oc_2">닫기</a></h3>
							<div id="acc_2" class="acc-section">
									
									<table class="pop_tbl_type1" style="width:744px;" cellspacing="0">
										<colgroup>
											<col width="200" />
											<col width="*"/>
										</colgroup>
										<tbody>
										<tr>
											<th scope="col">개발 검증 일자</th>
											<td>
												<input type="text" id="col27" name="col27" value="${PkgModel.col27}" maxlength="50" class="inp" readonly="readonly" />
												~
												<input type="text" id="col28" name="col28" value="${PkgModel.col28}" maxlength="50" class="inp" readonly="readonly" />
											</td>
										</tr>
										<tr>
											<th scope="col" height="30">개발 근거 문서</th>
											<td>
												<input type="text" id="col29" name="col29" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID29" onchange="fnSelBox29();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file40 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file41 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file42 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file43 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file44 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_29_1"><ui:file attachFileModel="${PkgModel.file40}" name="file40" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_29_1"><ui:file attachFileModel="${PkgModel.file40}" name="file40" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file41 == null }">
																<div id="display_29_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_2"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file42 == null }">
																<div id="display_29_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_3"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file43 == null }">
																<div id="display_29_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_4"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file44 == null }">
																<div id="display_29_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_5"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">신규 기능 규격서</th>
											<td>
												<input type="text" id="col10" name="col10" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID10" onchange="fnSelBox10();">
													<c:forEach var="selectItem" begin="1" end="5" step="1" varStatus="status">
														<c:choose>
															<c:when test="${PkgModel.file7 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file45 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file46 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file47 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file48 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_10_1"><ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_10_1"><ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file45 == null }">
																<div id="display_10_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_2"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file46 == null }">
																<div id="display_10_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_3"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file47 == null }">
																<div id="display_10_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_4"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file48 == null }">
																<div id="display_10_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_5"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">보완 내역서</th>
											<td>
												<input type="text" id="col30" name="col30" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID30" onchange="fnSelBox30();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file49 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file50 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file51 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file52 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file53 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_30_1"><ui:file attachFileModel="${PkgModel.file49}" name="file49" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_30_1"><ui:file attachFileModel="${PkgModel.file49}" name="file49" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file50 == null }">
																<div id="display_30_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_2"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file51 == null }">
																<div id="display_30_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_3"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file52 == null }">
																<div id="display_30_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_4"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file53 == null }">
																<div id="display_30_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_5"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												
											</td>
										</tr>
										<tr>
											<th scope="col">시험 절차서 </th>
											<td>
												<input type="text" id="col31" name="col31" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID31" onchange="fnSelBox31();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file54 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file55 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file56 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file57 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file58 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_31_1"><ui:file attachFileModel="${PkgModel.file54}" name="file54" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_31_1"><ui:file attachFileModel="${PkgModel.file54}" name="file54" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file55 == null }">
																<div id="display_31_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_2"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file56 == null }">
																<div id="display_31_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_3"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file57 == null }">
																<div id="display_31_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_4"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file58 == null }">
																<div id="display_31_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_5"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												
											</td>
										</tr>
										<tr>
											<th scope="col">코드 리뷰 및 SW<br/>아키텍처 리뷰  </th>
											<td>
												<input type="text" id="col32" name="col32" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID32" onchange="fnSelBox32();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file59 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file60 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file61 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file62 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file63 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_32_1"><ui:file attachFileModel="${PkgModel.file59}" name="file59" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_32_1"><ui:file attachFileModel="${PkgModel.file59}" name="file59" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file60 == null }">
																<div id="display_32_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_2"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file61 == null }">
																<div id="display_32_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_3"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file62 == null }">
																<div id="display_32_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_4"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file63 == null }">
																<div id="display_32_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_5"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col" height="30">기능 검증 결과 </th>
											<td>
												<input type="text" id="col12" name="col12" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID12" onchange="fnSelBox12();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file8 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file64 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file65== null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file66 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file67 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_12_1"><ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_12_1"><ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file64 == null }">
																<div id="display_12_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_2"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file65 == null }">
																<div id="display_12_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_3"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file66 == null }">
																<div id="display_12_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_4"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file67 == null }">
																<div id="display_12_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_5"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">성능용량 시험결과  </th>
											<td>
												<input type="text" id="col41" name="col41" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<select id="selID41" onchange="fnSelBox41();">
													<c:forEach var="selectItem" begin="1" end="5" step="1">
														<c:choose>
															<c:when test="${PkgModel.file69 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${PkgModel.file70 == null }">
																		<option><c:out value="${selectItem}" /></option>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${PkgModel.file71 == null }">
																				<c:if test="${status.index != 2}">
																					<option><c:out value="${selectItem}" /></option>
																				</c:if>
																				<c:if test="${status.index == 2}">
																					<option selected><c:out value="2" /></option>
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${PkgModel.file72 == null }">
																						<c:if test="${status.index != 3}">
																							<option><c:out value="${selectItem}" /></option>
																						</c:if>
																						<c:if test="${status.index == 3}">
																							<option selected><c:out value="3" /></option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${PkgModel.file73 == null }">
																								<c:if test="${status.index != 4}">
																									<option><c:out value="${selectItem}" /></option>
																								</c:if>
																								<c:if test="${status.index == 4}">
																									<option selected><c:out value="4" /></option>
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<option selected><c:out value="${selectItem}" /></option>
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>		
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_41_1"><ui:file attachFileModel="${PkgModel.file69}" name="file69" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_41_1"><ui:file attachFileModel="${PkgModel.file69}" name="file69" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file70 == null }">
																<div id="display_41_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_2"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file71 == null }">
																<div id="display_41_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_3"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file72 == null }">
																<div id="display_41_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_4"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file73 == null }">
																<div id="display_41_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_5"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
											</td>
										</tr>
										</tbody>
									</table>
							</div>
						</li>
						<li>			
									
							<h3 class="mag_top1">상용화 검증 <a class="btn-open" href="javascript:div_open_close('3');" id="oc_3">열기</a></h3>
							<div id="acc_3" class="acc-section" style="display: none;">
							
									<table class="pop_tbl_type1" style="width:744px;" cellspacing="0">
										<colgroup>
											<col width="200" />
											<col width="*" />
										</colgroup>
										<tbody>
										<tr>
											<th scope="col">보완내역서, 기능 변경 요청서</th>
											<td>
												<input type="text" id="col14" name="col14" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">보완내역별 검증 결과</th>
											<td>
												<input type="text" id="col16" name="col16" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">서비스 영향도 (로밍 포함)</th>
											<td>
												<input type="text" id="col18" name="col18" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">과금 영향도</th>
											<td>
												<input type="text" id="col20" name="col20" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">작업절차서,<br/>S/W 블록 내역 (list 및 size)</th>
											<td>
												<input type="text" id="col22" name="col22" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">PKG 적용 후 check list</th>
											<td>
												<input type="text" id="col24" name="col24" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">CoD/PoD 변경 사항,<br/>운용팀 공지사항</th>
											<td>
												<input type="text" id="col26" name="col26" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										<tr>
											<th scope="col">보안Guide 적용확인서 <span class='necessariness'>*</span></th>
											<td>
												<input type="text" id="col39" name="col39" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file68}" name="file68" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file68}" name="file68" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										
										</tbody>
									</table>
							</div>
									
								
						</li>
						<li>			
									
							<h3 class="mag_top1">추가첨부 <a class="btn-open" href="javascript:div_open_close('4');" id="oc_4">열기</a></h3>
							<div id="acc_4" class="acc-section" style="display: none;">
									
									<table class="pop_tbl_type4" style="width:744px;" cellspacing="0">
										<colgroup>
											<col width="200" />
											<col width="*" />
										</colgroup>
										<tr>
											<c:choose>
												<c:when test="${PkgModel.file27 == null }">
													<th id="selectRowspan" scope="col" height="30" rowspan="2">추가첨부</th>
												</c:when>
												<c:otherwise>
													<th id="selectRowspan" scope="col" height="30" rowspan="6">추가첨부</th>
												</c:otherwise>
											</c:choose>
											<td style="padding:6px 10px 4px;border-top:1px solid #e5e5e5; color:#4c4c4c;">
												<select id="selectID" onchange="fnSelectBoxCnt();">
													<c:forEach var="selectItem" begin="1" end="10" step="1">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<option><c:out value="${selectItem}" /></option>
															</c:when>
															<c:otherwise>
																<option selected><c:out value="${selectItem}" /></option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</td>
											<td style="padding:6px 10px 4px;border-top:1px solid #e5e5e5; color:#4c4c4c;"></td>
										</tr>
										<tr>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_file1"><ui:file attachFileModel="${PkgModel.file27}" name="file27" size="17" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_file1"><ui:file attachFileModel="${PkgModel.file27}" name="file27" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file2" style="display:none;"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file2"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file2" style="display:none;"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file3" style="display:none;"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file3"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file3" style="display:none;"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file4" style="display:none;"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file4"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file4" style="display:none;"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file5" style="display:none;"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file5"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file5" style="display:none;"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file6" style="display:none;"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file6"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file6" style="display:none;"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file7" style="display:none;"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file7"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file7" style="display:none;"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file8" style="display:none;"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file8"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file8" style="display:none;"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file9" style="display:none;"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file9"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file9" style="display:none;"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file27 == null }">
																<div id="display_file10" style="display:none;"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_file10"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_file10" style="display:none;"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="create" /></div>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>						
									</table>
									
							</div>
						</li>
					</ul>
</body>
