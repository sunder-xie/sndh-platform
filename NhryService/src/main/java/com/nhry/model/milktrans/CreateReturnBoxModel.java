package com.nhry.model.milktrans;

import com.nhry.data.milktrans.domain.TRecBot;
import com.nhry.data.milktrans.domain.TRecBotDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongjk on 2016/6/28.
 */
public class CreateReturnBoxModel implements Serializable {
    private TRecBot RecBot;
    private List<TRecBotDetail> entries = new ArrayList<TRecBotDetail>();

    public TRecBot getRecBot() {
        return RecBot;
    }

    public void setRecBot(TRecBot recBot) {
        RecBot = recBot;
    }

    public List<TRecBotDetail> getEntries() {
        return entries;
    }

    public void setEntries(List<TRecBotDetail> entries) {
        this.entries = entries;
    }
}
