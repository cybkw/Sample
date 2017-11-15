package com.frutacloud.rxjavaretrofitsample;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class TranslationBean {
    /**
     * status : 0
     * content : {"ph_en":"ˈhæpi","ph_am":"ˈhæpi","ph_en_mp3":"http://res.iciba.com/resource/amp3/oxford/0/43/7a/437a6065ca6f18f653616caabe015f2a.mp3","ph_am_mp3":"http://res.iciba.com/resource/amp3/1/0/56/ab/56ab24c15b72a457069c5ea42fcfc640.mp3","ph_tts_mp3":"http://res-tts.iciba.com/5/6/a/56ab24c15b72a457069c5ea42fcfc640.mp3","word_mean":["adj. 幸福的;快乐的;巧妙的;〈口〉有点醉意的;"]}
     */

    private int status;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * ph_en : ˈhæpi
         * ph_am : ˈhæpi
         * ph_en_mp3 : http://res.iciba.com/resource/amp3/oxford/0/43/7a/437a6065ca6f18f653616caabe015f2a.mp3
         * ph_am_mp3 : http://res.iciba.com/resource/amp3/1/0/56/ab/56ab24c15b72a457069c5ea42fcfc640.mp3
         * ph_tts_mp3 : http://res-tts.iciba.com/5/6/a/56ab24c15b72a457069c5ea42fcfc640.mp3
         * word_mean : ["adj. 幸福的;快乐的;巧妙的;〈口〉有点醉意的;"]
         */

        private String ph_en;
        private String ph_am;
        private String ph_en_mp3;
        private String ph_am_mp3;
        private String ph_tts_mp3;
        private List<String> word_mean;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "ph_en='" + ph_en + '\'' +
                    ", ph_am='" + ph_am + '\'' +
                    ", ph_en_mp3='" + ph_en_mp3 + '\'' +
                    ", ph_am_mp3='" + ph_am_mp3 + '\'' +
                    ", ph_tts_mp3='" + ph_tts_mp3 + '\'' +
                    ", word_mean=" + word_mean +
                    '}';
        }

        public String getPh_en() {
            return ph_en;
        }

        public void setPh_en(String ph_en) {
            this.ph_en = ph_en;
        }

        public String getPh_am() {
            return ph_am;
        }

        public void setPh_am(String ph_am) {
            this.ph_am = ph_am;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public String getPh_tts_mp3() {
            return ph_tts_mp3;
        }

        public void setPh_tts_mp3(String ph_tts_mp3) {
            this.ph_tts_mp3 = ph_tts_mp3;
        }

        public List<String> getWord_mean() {
            return word_mean;
        }

        public void setWord_mean(List<String> word_mean) {
            this.word_mean = word_mean;
        }
    }
}
