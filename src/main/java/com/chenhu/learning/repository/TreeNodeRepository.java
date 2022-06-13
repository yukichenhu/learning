package com.chenhu.learning.repository;

import com.chenhu.learning.entity.TreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陈虎
 * @date 2022-06-10 16:43
 */
public interface TreeNodeRepository extends JpaRepository<TreeNode,Integer> {
}
