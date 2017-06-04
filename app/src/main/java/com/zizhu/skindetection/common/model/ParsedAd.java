package com.zizhu.skindetection.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 1 on 2016/11/25.
 */
public class ParsedAd {
    public byte flags;
    public List<UUID> uuids = new ArrayList<UUID>();
    public String localName;
    public short manufacturer;
}
