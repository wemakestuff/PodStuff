package com.wemakestuff.podstuff.ui;

import android.os.Bundle;

import com.wemakestuff.podstuff.R;
import com.wemakestuff.podstuff.model.api.Episode;
import com.wemakestuff.podstuff.model.api.EpisodeCategory;
import com.wemakestuff.podstuff.model.navigation.EpisodeCategoryItem;
import com.wemakestuff.podstuff.model.navigation.EpisodeItem;
import com.wemakestuff.podstuff.model.navigation.Item;
import com.wemakestuff.podstuff.model.navigation.OnEpisodeClickListener;
import com.wemakestuff.podstuff.ui.base.BaseListActivity;
import com.wemakestuff.podstuff.ui.widget.adapter.EpisodeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemListActivity extends BaseListActivity implements OnEpisodeClickListener {
    EpisodeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new EpisodeCategoryItem(new EpisodeCategory("TOP EPISODES")));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeCategoryItem(new EpisodeCategory("ALL EPISODES")));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg",44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg", 44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg", 44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg", 44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg", 44236023L), this));
        itemList.add(new EpisodeItem(new Episode("Episode 83 | HitTail & AuditShark: Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels", "Mike and Rob discuss Integration Marketing, Logo Design, Survey Analysis and Leaky Funnels.<img src=\"http://feeds.feedburner.com/~r/StartupsForTheRestOfUs/~4/9oVMEIXh-1w\" height=\"1\" width=\"1\"/>", new Date(System.currentTimeMillis()), "http://www.project98.com/podcast/startups-for-the-rest-of-us-083.mp3", "http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_144x144.jpg", 44236023L), this));

        mAdapter = new EpisodeAdapter(this, itemList);
        setListAdapter(mAdapter);
    }

    @Override
    public void onEpisodeClick(Episode episode, Action action) {

    }
}
