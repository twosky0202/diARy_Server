package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "PlanLike")
public class PlanLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}