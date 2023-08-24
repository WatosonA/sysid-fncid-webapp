package com.example.sysid.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sysid.model.resources.StoreAttachedInfoResource;
import com.example.sysid.service.v900.StoreInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * 他サイト向け店舗情報紐付け取得API.
 * <pre>
 * リクエストパラメーターの店舗IDに紐づく店舗情報を取得する。
 * 紐づく情報がない場合はリクエストパラメーターの店舗IDのみを設定して返却する。
 * </pre>
 * ※このクラスのOpenAPIアノテーションは手動実装した例として（YAMLからInterfaceを生成する方式に決定した場合は書かない）
 */
@RestController
@RequestMapping("storeAttaches")
public class StoreAttachesControllerImpl implements StoreAttachesController {

    @Autowired
    protected StoreInfoService storeInfoService;

    /**
     * 他サイト向け店舗情報取得.
     */
    @GetMapping("list")
    @Operation(summary = "他サイト向け店舗情報取得",
            description = """
                    リクエストに指定された店舗IDリストに紐づく対向クライアントID、対向店舗IDのリストを返す
                    ※対向クライアントID／対向店舗IDが取得できない場合でも空で返す""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "正常終了、処理エラーの場合も含む",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = StoreAttachedInfoResource.class))))
            })
    @Nonnull
    public List<StoreAttachedInfoResource> getAttachedInfoList(
            @Valid @NotEmpty @RequestParam(value = "storeIdList", required = true)
            @Parameter(required = true, description = "店舗IDリスト", example = "KR00000001")
            final List<@NotBlank String> storeIdList) {

        return storeInfoService.getAttachedInfoList(storeIdList);
    }

}
