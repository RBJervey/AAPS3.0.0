package info.nightscout.androidaps.plugins.pump.omnipod.dash.driver.pod.command.base;

import java.nio.ByteBuffer;

import info.nightscout.androidaps.plugins.pump.omnipod.dash.driver.pod.util.CrcUtil;

public abstract class HeaderEnabledCommand implements Command {
    protected static final short HEADER_LENGTH = 6;

    protected final CommandType commandType;
    protected final int address;
    protected final short sequenceNumber;
    protected final boolean multiCommandFlag;

    protected HeaderEnabledCommand(CommandType commandType, int address, short sequenceNumber, boolean multiCommandFlag) {
        this.commandType = commandType;
        this.address = address;
        this.sequenceNumber = sequenceNumber;
        this.multiCommandFlag = multiCommandFlag;
    }

    @Override public CommandType getCommandType() {
        return commandType;
    }

    protected static byte[] appendCrc(byte[] command) {
        return ByteBuffer.allocate(command.length + 2) //
                .put(command) //
                .putShort(CrcUtil.createCrc(command)) //
                .array();
    }

    protected static byte[] encodeHeader(int address, short sequenceNumber, short length, boolean multiCommandFlag) {
        return ByteBuffer.allocate(6) //
                .putInt(address) //
                .putShort((short) (((sequenceNumber & 0x0f) << 10) | length | ((multiCommandFlag ? 1 : 0) << 15))) //
                .array();
    }

}
