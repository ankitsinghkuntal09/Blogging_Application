package com.blog.services;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE}) //Here Type means class
@Retention(RetentionPolicy.RUNTIME) //RunTime means it has runtime visibility.
@Qualifier
public @interface CustomAnnotation{ }

