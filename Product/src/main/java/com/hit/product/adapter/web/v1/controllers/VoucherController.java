package com.hit.product.adapter.web.v1.controllers;

import com.hit.product.adapter.web.base.RestApiV1;
import com.hit.product.adapter.web.base.VsResponseUtil;
import com.hit.product.applications.constants.UrlConstant;
import com.hit.product.applications.services.VoucherService;
import com.hit.product.domains.dtos.VoucherDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@ApiOperation(value = "Api Voucher")
public class VoucherController {
    
    @Autowired
    VoucherService voucherService;

    @ApiOperation(value = "Get All Voucher")
    @GetMapping(UrlConstant.Voucher.DATA_VOUCHER)
    public ResponseEntity<?> getVouchers() {
        return ResponseEntity.ok().body(voucherService.getVouchers());
    }

    @ApiOperation(value = "Get Voucher By Id")
    @GetMapping(UrlConstant.Voucher.DATA_VOUCHER_ID)
    public ResponseEntity<?> getVoucherById(@PathVariable("id") Long id) {
        return VsResponseUtil.ok(voucherService.getVoucherById(id));
    }

    @ApiOperation(value = "Create Voucher")
    @PostMapping(UrlConstant.Voucher.DATA_VOUCHER_CREATE)
    public ResponseEntity<?> createVoucher(@ModelAttribute VoucherDto VoucherDto,
                                           @RequestParam(name = "img", required = false) MultipartFile multipartFile) {
        return VsResponseUtil.ok(voucherService.createVoucher(VoucherDto, multipartFile));
    }

    @ApiOperation(value = "Create Voucher For Product")
    @PostMapping(UrlConstant.Voucher.DATA_VOUCHER_CREATE_FOR_PRODUCT)
    public ResponseEntity<?> createVoucherForProduct(@PathVariable("idProduct") Long idProduct,
                                                     @RequestBody VoucherDto VoucherDto,
                                                     @RequestParam(name = "img", required = false) MultipartFile multipartFile) {
        return VsResponseUtil.ok(voucherService.createVoucherForProduct(idProduct, VoucherDto, multipartFile));
    }

    @ApiOperation(value = "Add Voucher To User")
    @PostMapping(UrlConstant.Voucher.DATA_VOUCHER_GET_VOUCHER)
    public ResponseEntity<?> addVoucherToUser(@PathVariable("idUser") Long idUser, @PathVariable("idVoucher") Long idVoucher) {
        return VsResponseUtil.ok(voucherService.addVoucherToUser(idUser, idVoucher));
    }

    @ApiOperation(value = "Update Voucher")
    @PatchMapping(UrlConstant.Voucher.DATA_VOUCHER_ID)
    public ResponseEntity<?> updateVoucher(@PathVariable("id") Long id,
                                           @RequestBody VoucherDto VoucherDto,
                                           @RequestParam(name = "img", required = false) MultipartFile multipartFile) {
        return VsResponseUtil.ok(voucherService.updateVoucher(id, VoucherDto, multipartFile));
    }

    @ApiOperation(value = "Delete Voucher")
    @DeleteMapping(UrlConstant.Voucher.DATA_VOUCHER_ID)
    public ResponseEntity<?> deleteVoucher(@PathVariable("id") Long id) {
        return VsResponseUtil.ok(voucherService.deleteVoucher(id));
    }
}
