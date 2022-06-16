package com.chenhu.learning.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 陈虎
 * @since 2022-06-06 9:03
 */
@Component
public class MyPageHandlerMethodArgResolver extends PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
    private SortArgumentResolver sortResolver;

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

    private static final String JPA_PAGE_PARAM = "page";
    private static final String JPA_SIZE_PARAM = "size";

    private static final String DEFAULT_PAGE_PARAM = "page[number]";
    private static final String DEFAULT_SIZE_PARAM = "size[size]";

    public MyPageHandlerMethodArgResolver(@Nullable SortArgumentResolver sortResolver) {
        this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MyPageRequest.class.equals(parameter.getParameterType());
    }

    @NotNull
    @Override
    public MyPageRequest resolveArgument(@NotNull MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        String jpaPageStr = webRequest.getParameter(JPA_PAGE_PARAM);
        String jpaSizeStr = webRequest.getParameter(JPA_SIZE_PARAM);

        String pageStr = webRequest.getParameter(DEFAULT_PAGE_PARAM);
        String sizeStr = webRequest.getParameter(DEFAULT_SIZE_PARAM);

        Integer page = jpaPageStr != null ? Integer.valueOf(jpaPageStr) : pageStr != null ? Integer.valueOf(pageStr) : DEFAULT_PAGE;
        Integer size = jpaSizeStr != null ? Integer.valueOf(jpaSizeStr) : sizeStr != null ? Integer.valueOf(sizeStr) : DEFAULT_SIZE;

        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        return new MyPageRequest(page, size, sort);
    }
}
