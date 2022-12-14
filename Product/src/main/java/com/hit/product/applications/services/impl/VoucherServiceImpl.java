package com.hit.product.applications.services.impl;

import com.hit.product.adapter.web.v1.transfer.responses.TrueFalseResponse;
import com.hit.product.applications.repositories.ProductRepository;
import com.hit.product.applications.repositories.UserRepository;
import com.hit.product.applications.repositories.VoucherRepository;
import com.hit.product.applications.services.VoucherService;
import com.hit.product.applications.utils.UploadFile;
import com.hit.product.configs.exceptions.NotFoundException;
import com.hit.product.domains.dtos.VoucherDto;
import com.hit.product.domains.entities.Product;
import com.hit.product.domains.entities.User;
import com.hit.product.domains.entities.Voucher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UploadFile uploadFile;

    @Override
    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucherById(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        checkVoucherException(voucher);
        return voucher.get();
    }

    @Override
    @Transactional
    public Voucher createVoucher(VoucherDto voucherDto, MultipartFile multipartFile) {
        return createOrUpdate(new Voucher(), voucherDto, multipartFile);
    }

    @Override
    @Transactional
    public Voucher updateVoucher(Long id, VoucherDto voucherDto, MultipartFile multipartFile) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        checkVoucherException(voucher);
        return createOrUpdate(voucher.get(), voucherDto, multipartFile);
    }

    private Voucher createOrUpdate(Voucher voucher, VoucherDto voucherDto, MultipartFile multipartFile) {
        modelMapper.map(voucherDto, voucher);
        setImageVoucher(voucher, multipartFile);
        return voucherRepository.save(voucher);
    }

    @Override
    public TrueFalseResponse deleteVoucher(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        checkVoucherException(voucher);
        voucherRepository.deleteById(id);
        return new TrueFalseResponse(true);
    }

    @Override
    public TrueFalseResponse addVoucherToUser(Long idUser, Long idVoucher) {
        Optional<User> user = userRepository.findById(idUser);
        checkUserException(user);

        Optional<Voucher> voucher = voucherRepository.findById(idVoucher);
        checkVoucherException(voucher);

        voucherRepository.save(voucher.get());
        return new TrueFalseResponse(true);
    }

    @Override
    @Transactional
    public Voucher createVoucherForProduct(Long idProduct, VoucherDto voucherDto, MultipartFile multipartFile) {
        Optional<Product> product = productRepository.findById(idProduct);
        checkProductException(product);

        Voucher voucher = modelMapper.map(voucherDto, Voucher.class);
        voucher.setProduct(product.get());

        setImageVoucher(voucher, multipartFile);
        return voucherRepository.save(voucher);
    }

    public void setImageVoucher(Voucher voucher, MultipartFile multipartFile) {
        if(voucher.getUrlImage() != null) {
            uploadFile.removeFileFromUrl(voucher.getUrlImage());
        }
        voucher.setUrlImage(uploadFile.getUrlFromFile(multipartFile));
    }

    private void checkVoucherException(Optional<Voucher> voucher) {
        if(voucher.isEmpty()) {
            throw new NotFoundException("Not Found");
        }
    }

    private void checkUserException(Optional<User> user) {
        if(user.isEmpty()) {
            throw new NotFoundException("Not Found");
        }
    }

    private void checkProductException(Optional<Product> product) {
        if(product.isEmpty()) {
            throw new NotFoundException("Not Found");
        }
    }
}
