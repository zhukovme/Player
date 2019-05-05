package com.zhukovme.rxutils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Michael Zhukov on 03.05.2019.
 * email: zhukovme@gmail.com
 */
fun <T : Any?> Flowable<T>.applySchedulers(): Flowable<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Observable<T>.applySchedulers(): Observable<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Maybe<T>.applySchedulers(): Maybe<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Single<T>.applySchedulers(): Single<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun Completable.applySchedulers(): Completable =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Flowable<T>.applyMainSchedulers(): Flowable<T> =
        this.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Observable<T>.applyMainSchedulers(): Observable<T> =
        this.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Maybe<T>.applyMainSchedulers(): Maybe<T> =
        this.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Single<T>.applyMainSchedulers(): Single<T> =
        this.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())

fun Completable.applyMainSchedulers(): Completable =
        this.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())

fun <T : Any?> Flowable<T>.applyIoSchedulers(): Flowable<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

fun <T : Any?> Observable<T>.applyIoSchedulers(): Observable<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

fun <T : Any?> Maybe<T>.applyIoSchedulers(): Maybe<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

fun <T : Any?> Single<T>.applyIoSchedulers(): Single<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

fun Completable.applyIoSchedulers(): Completable =
        this.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

fun <T : Any?> Observable<T>.surround(onSubscribe: (() -> Unit)? = null,
                                      onTerminate: (() -> Unit)? = null,
                                      finally: (() -> Unit)? = null): Observable<T> =
        this.doOnSubscribe { onSubscribe?.invoke() }
                .doOnTerminate { onTerminate?.invoke() }
                .doFinally { finally?.invoke() }

fun <T : Any?> Maybe<T>.surround(onSubscribe: (() -> Unit)? = null,
                                 finally: (() -> Unit)? = null): Maybe<T> =
        this.doOnSubscribe { onSubscribe?.invoke() }
                .doFinally { finally?.invoke() }

fun <T : Any?> Single<T>.surround(onSubscribe: (() -> Unit)? = null,
                                  finally: (() -> Unit)? = null): Single<T> =
        this.doOnSubscribe { onSubscribe?.invoke() }
                .doFinally { finally?.invoke() }

fun Completable.surround(onSubscribe: (() -> Unit)? = null,
                         onTerminate: (() -> Unit)? = null,
                         finally: (() -> Unit)? = null): Completable =
        this.doOnSubscribe { onSubscribe?.invoke() }
                .doOnTerminate { onTerminate?.invoke() }
                .doFinally { finally?.invoke() }

fun Disposable.addTo(disposables: CompositeDisposable?) = disposables?.add(this)
