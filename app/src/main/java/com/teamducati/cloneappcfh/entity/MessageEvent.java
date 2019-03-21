package com.teamducati.cloneappcfh.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MessageEvent {

    private String address;

    private String pickShipAddress;

    private boolean onCurrentLocationClick;
}
