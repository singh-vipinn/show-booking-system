package com.vstech.model;

import com.vstech.pojo.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Show {

    private String name;
    private Genre genre;
}
