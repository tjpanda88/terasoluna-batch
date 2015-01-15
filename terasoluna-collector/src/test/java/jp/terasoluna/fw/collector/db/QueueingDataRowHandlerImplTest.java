package jp.terasoluna.fw.collector.db;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.ResultContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueingDataRowHandlerImplTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        QueueingDataRowHandlerImpl.setVerbose(true);
    }

    @After
    public void tearDown() throws Exception {
        QueueingDataRowHandlerImpl.setVerbose(false);
        Thread.interrupted();
    }

    /**
     * testHandleRow
     */
    @Test
    public void testHandleRow001() {
        QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DummyResultContext ctxInNull = new DummyResultContext();
        ctxInNull.setResultObject(null);
        assertNotNull(drh);
        try {
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * testHandleRow
     */
    @Test
    public void testHandleRow002() {
        QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();

        assertNotNull(drh);
        try {
            DummyResultContext context = new DummyResultContext();
            context.setResultObject("hoge1");
            drh.handleResult(context);
            context = new DummyResultContext();
            context.setResultObject("hoge2");
            drh.handleResult(context);
            DummyResultContext contextInNull = new DummyResultContext();
            contextInNull.setResultObject(null);
            drh.handleResult(contextInNull);
            context = new DummyResultContext();
            context.setResultObject("hoge3");
            drh.handleResult(context);
            context = new DummyResultContext();
            context.setResultObject("hoge4");
            drh.handleResult(context);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * testHandleRow
     */
    @Test
    public void testHandleRow003() {
        QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DBCollector<HogeBean> dbCollector = new DBCollectorStub004(5);
        drh.setDbCollector(dbCollector);

        assertNotNull(drh);

        try {
            DummyResultContext context = new DummyResultContext();
            context.setResultObject("hoge1");
            drh.handleResult(context);
            context.setResultObject("hoge2");
            drh.handleResult(context);
            DummyResultContext contextInNull = new DummyResultContext();
            contextInNull.setResultObject(null);
            drh.handleResult(contextInNull);
            context.setResultObject("hoge3");
            drh.handleResult(context);
            context.setResultObject("hoge4");
            drh.handleResult(context);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testHandleRow004() throws Exception {
        final QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DBCollector<HogeBean> dbCollector = new DBCollectorStub001();
        drh.setDbCollector(dbCollector);

        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() {
                Thread.currentThread().interrupt();
                try {
                    // 割り込み発生時はhandleRowは処理されず、InterruptedRuntimeExceptionが発生すること。
                    DummyResultContext context = new DummyResultContext();
                    context.setResultObject("hoge1");
                    drh.handleResult(context);
                    fail();
                } catch (InterruptedRuntimeException e) {
                    assertNull(e.getCause());
                    assertNull(drh.prevRow);
                }
            }
        };
        service.submit(runnable);
        runnable.throwErrorOrExceptionIfThrown();
    }

    /**
     * testHandleRow
     */
    @Test
    public void testHandleRow005() {
        QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DBCollectorStub001 dbCollector = new DBCollectorStub001();
        drh.setDbCollector(dbCollector);

        assertNotNull(drh);

        try {
            DummyResultContext context = new DummyResultContext();
            context.setResultObject("hoge1");
            drh.handleResult(context);
            context.setResultObject("hoge2");
            drh.handleResult(context);
            dbCollector.exceptionFlag = true;
            context.setResultObject("hoge3");
            drh.handleResult(context);
            fail();
        } catch (InterruptedRuntimeException e) {
        }
    }

    @Test
    public void testDelayCollect001() throws Exception {
        final QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DBCollectorStub004 dbCollector = new DBCollectorStub004(1);
        drh.setDbCollector(dbCollector);
        drh.prevRow = "rowObject";

        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() throws Exception {
                Thread.currentThread().interrupt();
                try {
                    // 割り込み発生時はdelayCollectは処理されず、InterruptedRuntimeExceptionが発生すること。
                    drh.delayCollect();
                    fail();
                } catch (InterruptedRuntimeException e) {
                    assertNull(e.getCause());
                }
            }
        };
        service.submit(runnable);
        runnable.throwErrorOrExceptionIfThrown();

        // 割り込み例外によりキューイングされていないこと。
        assertEquals(0, dbCollector.getQueue().size());
    }

    @Test
    public void testDelayCollect002() throws Exception {
        final QueueingDataRowHandlerImpl drh = new QueueingDataRowHandlerImpl();
        DBCollectorStub003 dbCollector = new DBCollectorStub003();
        drh.setDbCollector(dbCollector);
        drh.prevRow = "rowObject";
        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() throws Exception {
                try {
                    drh.delayCollect();
                    fail();
                } catch (InterruptedRuntimeException e) {
                    assertTrue(e.getCause() instanceof InterruptedException);
                }
            }
        };
        Future<?> future = service.submit(runnable);
        while (true) {
            try {
                // コレクタのキュー挿入スレッドがブロックされるまで少し待つ
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if (dbCollector.isBlocked()) {
                break;
            }
        }
        // タスクキャンセルを実行。
        future.cancel(true);
        runnable.throwErrorOrExceptionIfThrown();
    }

    /**
     * エラーをフィードバックできるRunnable実装。
     * 別スレッドで実施したい内容を doRun() throws Exception に実装する。
     * 試験終了時、throwErrorOrExceptionIfThrownメソッドを実行すると、
     * doRunメソッドにて想定外のエラーが発生した場合に、そのエラーがスローされる。
     */
    abstract class ErrorFeedBackRunnable implements Runnable {
        private Exception exception;
        private Error error;
        private CountDownLatch latch = new CountDownLatch(1);

        public void run() {
            try {
                doRun();
            } catch (Exception e) {
                exception = e;
            } catch (Error e) {
                error = e;
            } finally {
                latch.countDown();
            }
        }

        public void throwErrorOrExceptionIfThrown() throws Exception {
            latch.await();
            if (error != null) {
                throw error;
            } else if (exception != null) {
                throw exception;
            }
        }

        abstract void doRun() throws Exception;
    }

}
