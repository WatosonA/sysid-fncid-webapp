package com.example.sysid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sysid.model.dto.result.ResultIdAttachDto;
import com.example.sysid.model.resources.StoreAttachedInfoResource;
import com.example.sysid.repository.dao.IdAttachDao;

import jakarta.annotation.Nonnull;

/**
 * 他サイト向け店舗情報取得サービス.
 */
@Service
public class StoreInfoService {

    @Autowired
    protected IdAttachDao idAttachDao;

    /**
     * 引数の店舗IDに紐づく店舗IDとクライアントユーザIDを取得.
     * 紐づく情報がない場合はリクエストパラメーターの店舗IDのみを設定して返却する。
     * @param storeIdList 店舗IDリスト
     * @return 店舗紐付け情報リスト
     */
    @Nonnull
    public List<StoreAttachedInfoResource> getAttachedInfoList(@Nonnull List<String> storeIdList) {
        List<StoreAttachedInfoResource> resultList = new ArrayList<>(storeIdList.size());
        Map<String, ResultIdAttachDto> attachedMap = idAttachDao
                .selectAttachedInfo(storeIdList)
                .stream().collect(Collectors.toMap(e -> e.getStoreId(), e -> e));
        ResultIdAttachDto emptyDto = new ResultIdAttachDto();
        storeIdList.forEach(storeId -> {
            resultList.add(new StoreAttachedInfoResource()
                    .storeId(storeId)
                    .pairStoreId(attachedMap.getOrDefault(storeId, emptyDto).getPairStoreId())
                    .pairClientUserId(attachedMap.getOrDefault(storeId, emptyDto).getPairClientUserId()));
        });
        return resultList;
    }

}
