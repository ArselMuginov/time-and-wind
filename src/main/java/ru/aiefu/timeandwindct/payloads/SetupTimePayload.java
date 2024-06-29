package ru.aiefu.timeandwindct.payloads;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.aiefu.timeandwindct.TimeAndWindCT;

public record SetupTimePayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SetupTimePayload> ID = new CustomPacketPayload.Type<>(
        ResourceLocation.fromNamespaceAndPath(TimeAndWindCT.MOD_ID, "setup_time")
    );

    public static final StreamCodec<Object, SetupTimePayload> CODEC = StreamCodec.unit(new SetupTimePayload());

    @Override
    public @NotNull Type<SetupTimePayload> type() {
        return ID;
    }
}
