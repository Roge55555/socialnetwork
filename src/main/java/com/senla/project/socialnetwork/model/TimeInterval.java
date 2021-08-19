package com.senla.project.socialnetwork.model;

import lombok.Data;

@Data
public class TimeInterval<T> {

    private T from;

    private T to;

}
