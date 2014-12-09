/*
 * Copyright (c) 2011 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.batch.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.ex.unit.io.impl.CollectionSource;
import jp.terasoluna.fw.ex.unit.mock.MockQueryDao;
import jp.terasoluna.fw.ex.unit.mock.MockUpdateDao;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;
import jp.terasoluna.fw.ex.unit.util.AssertUtils;
import jp.terasoluna.fw.ex.unit.util.SystemEnvUtils;

import org.springframework.dao.DataAccessException;

public class JobUtilTest extends DaoTestCase {

    /**
     * 利用するDAOクラス
     */
    private QueryDAO queryDAO = null;

    private UpdateDAO updateDAO = null;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        deleteFromTable("job_control");

        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000001', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000002', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000003', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000004', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");

        queryDAO = getBean("adminQueryDAO");
        updateDAO = getBean("adminUpdateDAO");
    }

    /**
     * testSelectJobList01
     * @throws Exception
     */
    public void testSelectJobList01() throws Exception {

        List<BatchJobListResult> result = JobUtil.selectJobList(this.queryDAO);

        List<BatchJobListResult> except = new ArrayList<BatchJobListResult>();

        BatchJobListResult bean01 = new BatchJobListResult();
        bean01.setJobSequenceId("0000000001");
        BatchJobListResult bean02 = new BatchJobListResult();
        bean02.setJobSequenceId("0000000002");
        BatchJobListResult bean03 = new BatchJobListResult();
        bean03.setJobSequenceId("0000000003");
        BatchJobListResult bean04 = new BatchJobListResult();
        bean04.setJobSequenceId("0000000004");

        except.add(bean01);
        except.add(bean02);
        except.add(bean03);
        except.add(bean04);

        AssertUtils.assertInputEquals(new CollectionSource<BatchJobListResult>(
                except), new CollectionSource<BatchJobListResult>(result));

    }

    /**
     * testSelectJobList02
     * @throws Exception
     */
    public void testSelectJobList02() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList(this.queryDAO, 0,
                2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList03
     * @throws Exception
     */
    public void testSelectJobList03() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                this.queryDAO);

        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());
        assertEquals("0000000003", list.get(2).getJobSequenceId());
        assertEquals("0000000004", list.get(3).getJobSequenceId());

    }

    /**
     * testSelectJobList04
     * @throws Exception
     */
    public void testSelectJobList04() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                this.queryDAO, 0, 2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList05
     * @throws Exception
     */
    public void testSelectJobList05() throws Exception {

        List<String> curAppStatusList = new ArrayList<String>();

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                curAppStatusList, this.queryDAO, 0, 2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList06<br>
     * 事前準備：<br>
     * selectJobListメソッドに対して、以下の値を引数として実行<br>
     * ・開始インデックス、取得件数に-1の値を与える<br>
     * ・QueryDaoにMockQueryDaoを与える<br>
     * <br>
     * 期待結果：<br>
     * ・BatchExceptionが返ること<br>
     * <br>
     * @throws Exception
     */
    public void testSelectJobList06() throws Exception {
        try {
            JobUtil.selectJobList("hoge", new MockQueryDao(), -1, -1);
            fail("例外が発生しませんでした");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(BatchException.class, e.getClass());
        }
    }

    /**
     * testSelectJob01
     * @throws Exception
     */
    public void testSelectJob01() throws Exception {

        BatchJobData result = JobUtil.selectJob("0000000001", true,
                this.queryDAO);

        assertEquals("0000000001", result.getJobSequenceId());
        assertEquals("B000002", result.getJobAppCd());

    }

    /**
     * testSelectJob02
     * @throws Exception
     */
    public void testSelectJob02() throws Exception {
        MockQueryDao mockQueryDao = new MockQueryDao();
        mockQueryDao.addResult(new RuntimeException("例外発生時のメッセージ"));
        assertEquals(null, JobUtil.selectJob("hoge", true, mockQueryDao));
    }

    public void testSelectJob03() throws Exception {
        QueryDAO queryDAO = new QueryDAOStub01();

        try {
            JobUtil.selectJob("0000000001", true,
                queryDAO);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DBステータス取得時例外確認用", e.getMessage());
        }
    }

    /**
     * testGetCurrentTime01
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentTime01() throws Exception {

        Timestamp result = JobUtil.getCurrentTime(this.queryDAO);

        assertNotNull(result);
        System.out.println(result);

    }

    /**
     * testGetCurrentTime02
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentTime02() throws Exception {

        Timestamp result = JobUtil.getCurrentTime(null);

        assertNull(result);
    }

    public void testGetCurrentTime03() throws Exception {
        QueryDAO queryDAO = new QueryDAOStub01();
        try {
            JobUtil.getCurrentTime(queryDAO);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DBステータス取得時例外確認用", e.getMessage());
        }
    }

    /**
     * testGetCurrentDate01
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentDate01() throws Exception {

        Date result = JobUtil.getCurrentDate(this.queryDAO);

        assertNotNull(result);

        Date today = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sf.format(today), result.toString());

    }

    /**
     * testGetCurrentDate02
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentDate02() throws Exception {

        Date result = JobUtil.getCurrentDate(null);

        assertNull(result);

    }

    /**
     * testGetenv01
     * @throws Exception
     */
    public void testGetenv01() throws Exception {
        String result = JobUtil.getenv("");
        assertEquals("", result);
    }

    /**
     * testGetenv02<br>
     * 事前準備：<br>
     * 事前に以下のコマンドを設定し、環境変数を設定しておくこと<br>
     * eclipseで実行する際は実行の構成で設定すること<br>
     * SET JOB_APP_CD=B000001
     * @throws Exception
     */
    public void testGetenv02() throws Exception {
        try {
            SystemEnvUtils.setEnv("JOB_APP_CD", "B000001");
            String result = JobUtil.getenv("JOB_APP_CD");
            assertEquals("B000001", result);
        } finally {
            SystemEnvUtils.restoreEnv();
        }
    }

    /**
     * testUpdateJobStatus01
     * @throws Exception
     */
    public void testUpdateJobStatus01() throws Exception {

        boolean result = JobUtil.updateJobStatus("0000000002", "0", null, null,
                this.queryDAO, this.updateDAO);

        assertEquals(true, result);

    }

    /**
     * testUpdateJobStatus02<br>
     * 事前準備：<br>
     * 1. updateJobStatusメソッドに対して、以下の値を引数として実行<br>
     * ・UpdateDaoにMockUpdateDaoExを与える<br>
     * ・MockUpdateDaoExはthrowExceptionが正の場合、executeメソッド実行の際に例外を投げるものとする<br>
     * 2.updateJobStatusメソッドに対して、以下の値を引数として実行<br>
     * ・UpdateDaoにMockUpdateDaoを与える<br>
     * ・MockUpdateDaoの結果に-1の値を与える 期待結果：<br>
     * 1.falseが返ること(実行メソッド内のupdateDao.executeメソッドで更新失敗値の-1が返る)<br>
     * @throws Exception
     */
    public void testUpdateJobStatus02() throws Exception {
        MockUpdateDao mockUpdateDao = new MockUpdateDao();
        mockUpdateDao.addResult(new RuntimeException("例外発生時のメッセージ"));
        assertFalse(JobUtil.updateJobStatus("hoge", "piyo", "foo", "bar",
                queryDAO, mockUpdateDao));
    }

    /**
     * testUpdateJobStatus03<br>
     * 事前準備：<br>
     * 1.updateJobStatusメソッドに対して、以下の値を引数として実行<br>
     * ・UpdateDaoにMockUpdateDaoを与える<br>
     * ・MockUpdateDaoの結果に-1の値を与える 期待結果：<br>
     * 1.falseが返ること(実行メソッド内で例外が発生する)<br>
     * <br>
     * @throws Exception
     */
    public void testUpdateJobStatus03() throws Exception {
        MockUpdateDao mockUpdateDao = new MockUpdateDao();
        mockUpdateDao.addResult(-1);
        assertFalse(JobUtil.updateJobStatus("hoge", "piyo", "foo", "bar",
                queryDAO, mockUpdateDao));
    }

    public void testUpdateJobStatus04() throws Exception {
        UpdateDAO updateDAO = new UpdateDAOStub01();
        try {
            JobUtil.updateJobStatus("0000000002", "0", null, null,
                this.queryDAO, updateDAO);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DBステータス更新時例外確認用", e.getMessage());
        }
    }

	/**
     * testJobUtil001
     * @throws Exception
     */
    public void testJobUtil001() throws Exception {
        JobUtil ju = new JobUtil();
        assertNotNull(ju);
    }
}
