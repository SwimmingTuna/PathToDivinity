package net.swimmingtuna.pathtodivinity.profile;

import net.minecraft.ChatFormatting;

import javax.annotation.Nullable;

public enum ProfileType {

    NORMAL("normal", "Normal", ChatFormatting.GOLD),

    SAFEMODE("safemode", "Safemode", ChatFormatting.AQUA);

    private final String id;
    private final String displayName;
    private final ChatFormatting color;

    ProfileType(String id, String displayName, ChatFormatting color) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ChatFormatting getColor() {
        return this.color;
    }

    /** The profile that is not this one. With exactly two profiles this is always well defined. */
    public ProfileType other() {
        return this == NORMAL ? SAFEMODE : NORMAL;
    }

    /** Parses a stored id back to a profile, or {@code null} when the id is absent or unrecognised. */
    @Nullable
    public static ProfileType byId(@Nullable String id) {
        if (id == null) {
            return null;
        }
        for (ProfileType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }
}
