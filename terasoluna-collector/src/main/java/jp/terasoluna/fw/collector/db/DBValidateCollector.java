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

package jp.terasoluna.fw.collector.db;

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ExceptionValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;

import org.springframework.validation.Validator;

/**
 * DBValidateCollector<br>
 * 独立した別スレッドを起動し、QueryRowHandleDAOを非同期で実行する。
 * @param &ltP&gt
 */
public class DBValidateCollector<P> extends DBCollector<P> {

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param validator Validator 入力チェックを行うバリデータ
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, Validator validator) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addValidator(validator));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param validator Validator 入力チェックを行うバリデータ
     * @param validationErrorHandler ValidationErrorHandler 入力チェックエラー時に行う処理
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param relation1n 1:Nマッピング使用時はtrue
     * @param validator Validator 入力チェックを行うバリデータ
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, boolean relation1n,
            Validator validator) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addRelation1n(relation1n).addValidator(validator));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param relation1n 1:Nマッピング使用時はtrue
     * @param validator Validator 入力チェックを行うバリデータ
     * @param validationErrorHandler ValidationErrorHandler 入力チェックエラー時に行う処理
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, boolean relation1n,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addRelation1n(relation1n).addValidator(validator)
                .addValidationErrorHandler(validationErrorHandler));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param validator Validator 入力チェックを行うバリデータ
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, Validator validator) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addValidator(validator));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param validator Validator 入力チェックを行うバリデータ
     * @param validationErrorHandler ValidationErrorHandler 入力チェックエラー時に行う処理
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addValidator(validator)
                .addValidationErrorHandler(validationErrorHandler));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param exceptionHandler 例外ハンドラ
     * @param validator Validator 入力チェックを行うバリデータ
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param exceptionHandler 例外ハンドラ
     * @param validator Validator 入力チェックを行うバリデータ
     * @param validationErrorHandler ValidationErrorHandler 入力チェックエラー時に行う処理
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param relation1n 1:Nマッピング使用時はtrue
     * @param exceptionHandler 例外ハンドラ
     * @param dbCollectorPrePostProcess DBCollector前後処理
     * @param validator Validator 入力チェックを行うバリデータ
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DBCollectorPrePostProcess dbCollectorPrePostProcess,
            Validator validator) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDbCollectorPrePostProcess(dbCollectorPrePostProcess)
                .addValidator(validator));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param queryRowHandleDao QueryRowHandleDaoインスタンス
     * @param methodName 実行するメソッド名
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @param queueSize キューのサイズ（1以上を設定すること）
     * @param relation1n 1:Nマッピング使用時はtrue
     * @param exceptionHandler 例外ハンドラ
     * @param dbCollectorPrePostProcess DBCollector前処理
     * @param validator Validator 入力チェックを行うバリデータ
     * @param validationErrorHandler ValidationErrorHandler 入力チェックエラー時に行う処理
     */
    public DBValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DBCollectorPrePostProcess dbCollectorPrePostProcess,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DBCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDbCollectorPrePostProcess(dbCollectorPrePostProcess)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DBValidateCollectorコンストラクタ<br>
     * @param config DBCollectorConfig DBCollector設定項目
     */
    public DBValidateCollector(DBCollectorConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter is null.");
        }

        this.queryRowHandleDao = config.getQueryRowHandleDao();
        this.methodName = config.getMethodName();
        this.bindParams = config.getBindParams();
        if (config.getQueueSize() > 0) {
            setQueueSize(config.getQueueSize());
        }
        if (config.isRelation1n()) {
            this.queueingDataRowHandlerClass = Queueing1NRelationDataRowHandlerImpl.class;
        }
        this.validator = config.getValidator();
        if (config.getValidator() != null) {
            if (config.getValidationErrorHandler() != null) {
                this.validationErrorHandler = config
                        .getValidationErrorHandler();
            } else {
                this.validationErrorHandler = new ExceptionValidationErrorHandler();
            }
        }
        this.exceptionHandler = config.getExceptionHandler();
        this.dbCollectorPrePostProcess = config.getDbCollectorPrePostProcess();

        if (config.isExecuteByConstructor()) {
            // 実行開始
            execute();
        }
    }

}
