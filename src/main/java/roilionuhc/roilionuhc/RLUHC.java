package roilionuhc.roilionuhc;

import fr.supercomete.head.GameUtils.Command.KasterborousCommand;
import fr.supercomete.head.GameUtils.Command.ModeCommand;
import fr.supercomete.head.GameUtils.Command.SubCommand;
import fr.supercomete.head.GameUtils.DeathCause;
import fr.supercomete.head.GameUtils.GameMode.ModeModifier.CampMode;
import fr.supercomete.head.GameUtils.GameMode.ModeModifier.Command;
import fr.supercomete.head.GameUtils.GameMode.Modes.Mode;
import fr.supercomete.head.GameUtils.HistoricData;
import fr.supercomete.head.GameUtils.Scenarios.Compatibility;
import fr.supercomete.head.GameUtils.Scenarios.CompatibilityType;
import fr.supercomete.head.PlayerUtils.EffectNullifier;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import roilionuhc.roilionuhc.role.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;


public class RLUHC extends Mode implements CampMode, Command {
    public RLUHC() {
        super("Roi Lion UHC", Material.GOLD_BLOCK, Collections.emptyList());
    }

    @Override
    public void OnKillMethod(Location location, Player player, Player player1) {
    }

    @Override
    public void onAnyTime(Player player) {

    }

    @Override
    public void onGlobalAnytime(int i) {

    }

    @Override
    public void onDayTime(Player player) {

    }

    @Override
    public void onNightTime(Player player) {

    }

    @Override
    public void onEndingTime(Player player) {

    }

    @Override
    public void onRoleTime(Player player) {

    }

    @Override
    public void OnStart(Player player) {

    }

    @Override
    public void onEpisodeTime(Player player) {

    }

    @Override
    public boolean WinCondition() {
        return false;
    }


    @Override
    public KasterborousCommand getCommand() {
        final KasterborousCommand command =new ModeCommand("rlu",new Compatibility(CompatibilityType.WhiteList,new Class[]{RLUHC.class}));
        command.addSubCommand(new SubCommand() {
            @Override
            public String subCommand() {
                return "crie";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                if (RoiLionUHC.api.getRoleProvider().getRoleOf(player) instanceof Ed) {
                    final Ed ed = (Ed) RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                    if (!ed.crie) {
                        ed.crie = true;
                        player.sendMessage("§7Tous les joueurs dans un rayon de 50blocs ont perdu leurs effets.");
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (RoiLionUHC.api.getGameProvider().getPlayerList().contains(other.getUniqueId())&& RoiLionUHC.api.getRoleProvider().getRoleOf(other).getCamp()!=RLTeams.Hyennes) {
                                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(other, KTBSEffect.getNullifer(20 * 8, new ArrayList<>(), EffectNullifier.Type.WHITELIST));
                            }
                        }
                    } else player.sendMessage(Main.UHCTypo + "§cVous avez déja utilisé cette commande.");
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable par Ed uniquement.";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "death";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                if (role instanceof Nala) {
                    final Nala nala = (Nala) role;
                    if (nala.used) {
                        player.sendMessage("Vous avez atteint le nombre maximum d'utilisation de cette commande pour cet épisode.");
                        return true;
                    }
                    if (strings.length == 0) {
                        player.sendMessage("§cUsage: /rl death <Joueur>");
                        return true;
                    }
                    String target_str = strings[0];
                    final Player target = Bukkit.getPlayer(target_str);
                    if (target == null || !Main.getPlayerlist().contains(target.getUniqueId())) {
                        player.sendMessage("§cJoueur introuvable.");
                        return true;
                    }
                    int kill = RoiLionUHC.api.getGameProvider().getCurrentGame().getKillList().get(target.getUniqueId()) != null ? RoiLionUHC.api.getGameProvider().getCurrentGame().getKillList().get(target.getUniqueId()) : 0;
                    player.sendMessage("§7Le joueur §c" + target.getName() + "§7 a §6" + kill + "§7 kill.");
                    nala.used = true;
                    Random random = new Random();
                    int r = random.nextInt(5);
                    target.sendMessage("§7Vous avez été espionné par Nala.");
                    boolean hasKilled = false;
                    final ArrayList<String> arrayList = new ArrayList<>();
                    Bukkit.getLogger().log(Level.INFO, RoleHandler.getHistoric() + "");
                    for (HistoricData historicData : RoleHandler.getHistoric().getRoleList().values()) {
                        DeathCause cause = historicData.getCause();
                        if (cause == null) {
                            continue;
                        }
                        String killer = cause.getKillerName();
                        if (killer.equals(player.getName())) {
                            arrayList.add(historicData.getPlayer().getUsername());
                            if (historicData.getRole().getDefaultCamp() == RLTeams.LionEarth) {
                                hasKilled = true;
                            }
                        }
                    }
                    if (r == 0) {
                        player.sendMessage("Voici les joueurs que " + target.getName() + " a tué: ");
                        for (String string : arrayList) {
                            player.sendMessage("    " + string);
                        }
                    }
                    if (hasKilled) {
                        player.sendMessage("§dCe joueur a tué un joueur de votre camp. Par conséquent vous avez 15% de force pendant 1min30.");
                        RoiLionUHC.api.getPotionEffectProvider().addBonus(player, new Bonus(BonusType.Force, 15));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                RoiLionUHC.api.getPotionEffectProvider().addBonus(player, new Bonus(BonusType.Force, -15));
                                player.sendMessage("§cVous avez perdu vos 15% de force.");
                            }
                        }.runTaskLater(RoiLionUHC.INSTANCE, 20 * 90);
                    }


                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable par Nala uniquement.";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "coord";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                if (RoiLionUHC.api.getRoleProvider().isRoleGenerated()) {
                    final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                    if (role instanceof Zazu) {
                        final Zazu zazu = (Zazu) role;
                        if (strings.length < 1) {
                            player.sendMessage("Usage: /rlu coord <Joueur>");
                            return true;
                        }
                        final String str = strings[0];
                        final Player target = Bukkit.getPlayer(str);
                        if (RoiLionUHC.api.getPlayerHelper().IsPlayerInGame(target.getUniqueId())) {
                            if (zazu.used_coord < 3) {
                                if (target.getHealth() <= 16) {
                                    zazu.used_coord++;
                                    final int x = (int) target.getLocation().getX();
                                    final int y = (int) target.getLocation().getY();
                                    final int z = (int) target.getLocation().getZ();
                                    player.sendMessage("§7Le joueur §6" + target.getName() + "§7 est au coordonée: §c" + x + " " + y + " " + z);
                                } else {
                                    player.sendMessage("§cCe joueur a trop de vie.");
                                }
                            } else {
                                player.sendMessage("§cVous avez atteint le maximumu d'utilisation de ce pouvoir.");
                            }

                        } else {
                            player.sendMessage("§cCe joueur n'est pas connecté.");
                        }
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Zazu";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "simba";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                if (role instanceof Sarabi) {
                    Sarabi sarabi = (Sarabi) role;
                    if (!sarabi.exchange) {
                        sarabi.exchange = true;
                        sarabi.addBonus(new Bonus(BonusType.Heart, -4));
                        player.sendMessage("Simba est "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Simba.class));
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Sarabi.";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "invi";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                if(RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(player.getUniqueId())){
                    final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                    if(role instanceof Kion){
                        final Kion kion = (Kion)role;
                        if(kion.coolDown.getUtilisation()>0){
                            if(kion.coolDown.isAbleToUse()){
                                kion.coolDown.setUseNow();
                                kion.coolDown.addUtilisation(-1);
                                kion.remaning_time=15*60;
                                player.sendMessage("§7Vous êtes invisible pour les 15 prochaines minutes.");
                            }else player.sendMessage("§7Vous ne pouvez pas utiliser cette capacité.");
                        }else player.sendMessage("§7Vous avez atteint le maximum d'utilisation de cette capacité.");
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Kion";
            }
        });
        return command;
    }
}
