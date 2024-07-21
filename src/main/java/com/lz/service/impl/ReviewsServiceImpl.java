package com.lz.service.impl;

import com.lz.mapper.TaskMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.dto.ReviewsDTO;
import com.lz.pojo.entity.Reviews;
import com.lz.mapper.ReviewsMapper;
import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.Users;
import com.lz.service.IReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.service.ITaskService;
import com.lz.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 存储用户对任务的评价信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements IReviewsService {
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private ReviewsMapper reviewsMapper;
    
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public void save(ReviewsDTO reviewsDTO) {
        Task task = taskMapper.selectById(reviewsDTO.getTaskId());
        if (task.getStatus()!= TaskStatus.COMPLETED){
            throw new RuntimeException("任务未完成，不能评价");
        }
        
        Reviews reviews = Reviews.builder()
                .taskId(reviewsDTO.getTaskId())
                .reviewerId(getCurrentAdmin().getUserId())
                .acceptorId(task.getReceiverId())
                .publisherId(task.getOwnerId())
                .comment(reviewsDTO.getComment())
                .rating(reviewsDTO.getRate())
                .isApproved(false)
                .build();
        reviewsMapper.insert(reviews);
        
        
    }

    @Override
    public List<Reviews> exportExcel() {
        List<Reviews> reviews = reviewsMapper.selectList(null);
        if (!reviews.isEmpty()){
            return reviews;
        }
        return null;
    }

    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }
}