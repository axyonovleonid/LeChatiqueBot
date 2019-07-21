package axyonovleonid;

public class ChatTimers {
    private int chatId = 0;
    private Long imageTimer = 60L;
    private Long gifTimer = 60L;
    private Long videoTimer = 60L;
    private Long stickerTimer = 60L;
    private Long animatedStickerTimer = 60L;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Long getImageTimer() {
        return imageTimer;
    }

    public void setImageTimer(Long imageTimer) {
        this.imageTimer = imageTimer;
    }

    public Long getGifTimer() {
        return gifTimer;
    }

    public void setGifTimer(Long gifTimer) {
        this.gifTimer = gifTimer;
    }

    public Long getVideoTimer() {
        return videoTimer;
    }

    public void setVideoTimer(Long videoTimer) {
        this.videoTimer = videoTimer;
    }

    public Long getAnimatedStickerTimer() {
        return animatedStickerTimer;
    }

    public void setAnimatedStickerTimer(Long animatedStickerTimer) {
        this.animatedStickerTimer = animatedStickerTimer;
    }

    public Long getStickerTimer() {
        return stickerTimer;
    }

    public void setStickerTimer(Long stickerTimer) {
        this.stickerTimer = stickerTimer;
    }
}