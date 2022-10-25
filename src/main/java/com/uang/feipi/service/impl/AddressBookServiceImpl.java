package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.mapper.AddressBookMapper;
import com.uang.feipi.poji.AddressBook;
import com.uang.feipi.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
