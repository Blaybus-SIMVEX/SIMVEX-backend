package com.simvex.backend.domain.object3d.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    MECHANICAL_ENGINEERING("기계공학", null),
    AUTOMOTIVE("자동차",MECHANICAL_ENGINEERING),
    AEROSPACE("항공", MECHANICAL_ENGINEERING),
    MACHINE_TOOLS("공작기계", MECHANICAL_ENGINEERING),
    ROBOTICS("로봇공학", MECHANICAL_ENGINEERING);

    private final String koreanName;
    private final Category parent;
}