package com.hyj.cloud.model.dto;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyj.cloud.utils.BeanUtils;
import com.hyj.cloud.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {

    protected Long total;

    protected Long pages;

    protected List<T> list;

    public static <T> PageDTO<T> empty(Long total, Long pages) {
        return new PageDTO<>(total, pages, Collections.emptyList());
    }
    public static <T> PageDTO<T> empty(Page<?> page) {
        return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
    }

    public static <T> PageDTO<T> of(Page<T> page) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(), page.getRecords());
    }
    public static <T,R> PageDTO<T> of(Page<R> page, Function<R, T> mapper) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(),
                page.getRecords().stream().map(mapper).collect(Collectors.toList()));
    }
    public static <T> PageDTO<T> of(Page<?> page, List<T> list) {
        return new PageDTO<>(page.getTotal(), page.getPages(), list);
    }


    public static <T, R> PageDTO<T> of(Page<R> page, Class<T> clazz, Convert<R, T> convert) {
        return new PageDTO<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getRecords(), clazz, convert));
    }

    @JsonIgnore
    public boolean isEmpty(){
        return list == null || list.size() == 0;
    }
}
