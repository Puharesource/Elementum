package io.puharesource.mc.elementum.core.api.user

/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
class Mail {
    private String text
    private long time
    private UUID sender

    public Mail(String text, UUID sender) {
        this.text = text
        time = System.currentTimeMillis()
        this.sender = sender
    }

    String getText() { text }
    long getTime() { time }
    UUID getUuid() { sender }
}
