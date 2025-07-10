package com.artillexstudios.axtrade.utils;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VaultHelper {
    private static Chat chat = null;
    private static boolean vaultEnabled = false;

    public static void setupChat() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            vaultEnabled = false;
            return;
        }

        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            vaultEnabled = false;
            return;
        }

        chat = rsp.getProvider();
        vaultEnabled = chat != null;
    }

    public static boolean isVaultEnabled() {
        return vaultEnabled && chat != null;
    }

    @Nullable
    public static String getPlayerPrefix(@NotNull Player player) {
        if (!isVaultEnabled()) return null;
        try {
            return chat.getPlayerPrefix(player);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String getPlayerSuffix(@NotNull Player player) {
        if (!isVaultEnabled()) return null;
        try {
            return chat.getPlayerSuffix(player);
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public static String getDisplayName(@NotNull Player player) {
        if (!isVaultEnabled()) {
            return player.getName();
        }

        try {
            String prefix = chat.getPlayerPrefix(player);
            String suffix = chat.getPlayerSuffix(player);
            String name = player.getName();

            StringBuilder displayName = new StringBuilder();
            
            if (prefix != null && !prefix.isEmpty()) {
                displayName.append(prefix);
            }
            
            displayName.append(name);
            
            if (suffix != null && !suffix.isEmpty()) {
                displayName.append(suffix);
            }

            return displayName.toString();
        } catch (Exception e) {
            return player.getName();
        }
    }

    @NotNull
    public static String getFormattedPlayerName(@NotNull Player player) {
        return getDisplayName(player);
    }
} 