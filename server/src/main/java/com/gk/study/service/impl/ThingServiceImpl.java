package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Company;
import com.gk.study.entity.Thing;
import com.gk.study.entity.ThingTag;
import com.gk.study.mapper.ThingMapper;
import com.gk.study.mapper.ThingTagMapper;
import com.gk.study.service.CompanyService;
import com.gk.study.service.TagService;
import com.gk.study.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThingServiceImpl extends ServiceImpl<ThingMapper, Thing> implements ThingService {
    @Autowired
    ThingMapper mapper;

    @Autowired
    CompanyService companyService;

    @Autowired
    ThingTagMapper thingTagMapper;

    @Autowired
    TagService tagService;

    @Override
    public List<Thing> getThingList(String keyword, String sort, String c, String tag) {
        QueryWrapper<Thing> queryWrapper = new QueryWrapper<>();

        // 搜索
        queryWrapper.like(StringUtils.isNotBlank(keyword), "title", keyword);

        // 排序
        if (StringUtils.isNotBlank(sort)) {
            if (sort.equals("recent")) {
                queryWrapper.orderBy(true, false, "create_time");
            } else if (sort.equals("hot") || sort.equals("recommend")) {
                queryWrapper.orderBy(true, false, "pv");
            }
        }else {
            queryWrapper.orderBy(true, false, "create_time");
        }

        // 根据分类筛选
        if (StringUtils.isNotBlank(c) && !c.equals("-1")) {
            queryWrapper.eq(true, "classification_id", c);
        }

        List<Thing> things = mapper.selectList(queryWrapper);

        things.forEach(thing -> {
             Company tmp=companyService.getCompanyById(thing.companyId);
             thing.setCover(tmp.getCover());
             thing.setCompanyName(tmp.getTitle());
        });

        // tag筛选
        if (StringUtils.isNotBlank(tag)) {
            List<Thing> tThings = new ArrayList<>();
            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.eq("tag_id", tag);
            List<ThingTag> thingTagList = thingTagMapper.selectList(thingTagQueryWrapper);
            for (Thing thing : things) {
                for (ThingTag thingTag : thingTagList) {
                    if (thing.getId().equals(thingTag.getThingId())) {
                        tThings.add(thing);
                    }
                }
            }
            things.clear();
            things.addAll(tThings);
        }

        // 附加tag
        for (Thing thing : things) {
            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.lambda().eq(ThingTag::getThingId, thing.getId());
            List<ThingTag> thingTags = thingTagMapper.selectList(thingTagQueryWrapper);
            List<String> tags = thingTags.stream().map((thingTag)->{
                long tagId=thingTag.getTagId();
                return tagService.getTag(tagId).getTitle();
            }).collect(Collectors.toList());
            List<Long> tagids = thingTags.stream().map((thingTag)->{
                long tagId=thingTag.getTagId();
                return tagId;
            }).collect(Collectors.toList());
            thing.setTags(tags);
            thing.setTagids(tagids);
        }
        return things;
    }

    @Override
    public void createThing(Thing thing) {
        System.out.println(thing);
        thing.setCreateTime(new Timestamp(System.currentTimeMillis()));

//        if (thing.getPv() == null) {
//            thing.setPv("0");
//        }

//        if (thing.getWishCount() == null) {
//            thing.setWishCount("0");
//        }
        mapper.insert(thing);
        // 更新tag
        setThingTags(thing);
    }

    @Override
    public void deleteThing(String id) {
        long realId=Long.parseLong(id);
        mapper.deleteById(realId);
    }

    @Override
    public void updateThing(Thing thing) {

        // 更新tag
        setThingTags(thing);

        mapper.updateById(thing);
    }

    @Override
    public Thing getThingById(long id) {
        Thing ret = mapper.selectById(id);
        Company tmp=companyService.getCompanyById(ret.companyId);
        ret.setCover(tmp.getCover());
        return ret;
    }

    // 心愿数加1
    @Override
    public void addWishCount(String thingId) {
        Thing thing = mapper.selectById(thingId);
        thing.setWishCount(thing.getWishCount() + 1);
        mapper.updateById(thing);
    }

    // 收藏数加1
    @Override
    public void addCollectCount(String thingId) {
        Thing thing = mapper.selectById(thingId);
        thing.setCollectCount(thing.getCollectCount() + 1);
        mapper.updateById(thing);
    }

    @Override
    public List<Thing> getUserThing(String userId) {
        QueryWrapper<Thing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return mapper.selectList(queryWrapper);
    }

    public void setThingTags(Thing thing) {
        // 删除tag
        Map<String, Object> map = new HashMap<>();
        map.put("thing_id", thing.getId());
        thingTagMapper.deleteByMap(map);
        // 新增tag
        if (thing.getTagids() != null) {
            for (Long tag : thing.getTagids()) {
                ThingTag thingTag = new ThingTag();
                thingTag.setThingId(thing.getId());
                thingTag.setTagId(tag);
                thingTagMapper.insert(thingTag);
            }
        }
    }

}
