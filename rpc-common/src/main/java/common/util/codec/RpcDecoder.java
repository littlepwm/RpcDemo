package common.util.codec;

import common.util.Serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

  private Class<?> clazz;

  private Serializer serializer;

  public RpcDecoder(Class<?> clazz, Serializer serializer) {
    this.clazz = clazz;
    this.serializer = serializer;
  }

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
      throws Exception {
    if (byteBuf.readableBytes() < 4) {
      return;
    }
    //标记当前读的位置
    byteBuf.markReaderIndex();
    int dataLength = byteBuf.readInt();
    if (byteBuf.readableBytes() < dataLength) {
      byteBuf.resetReaderIndex();
      return;
    }
    byte[] data = new byte[dataLength];
    //将byteBuf中的数据读入data字节数组
    byteBuf.readBytes(data);
    Object obj = serializer.deserialize(clazz, data);
    list.add(obj);

  }
}
