package roilionuhc.roilionuhc;

import fr.supercomete.enums.Gstate;
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
import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.GameUtils.WinCondition;
import fr.supercomete.head.PlayerUtils.EffectNullifier;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.structure.Structure;
import fr.supercomete.head.structure.StructureHandler;
import fr.supercomete.head.world.scoreboardmanager;
import fr.supercomete.tasks.NoDamage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import roilionuhc.roilionuhc.role.*;

import java.util.*;
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
        if(RoleHandler.IsRoleGenerated()) {
            if(RoleHandler.getRoleList().size()== 0 ) {
                scoreboardmanager.titlemessage("Victoire de la §4Mort");
                return true;
            }
            boolean rlcamp=false;
            boolean hyennes=false;
            boolean Duo_Rani_Kion=false;
            boolean scar=false;
            boolean rejected=false;
            boolean neutral=false;
            for(Map.Entry<UUID,Role> entry:RoleHandler.getRoleList().entrySet()){
                KasterBorousCamp camps = entry.getValue().getCamp();
                if(camps==RLTeams.LionEarth){
                    rlcamp=true;
                }
                if(camps==RLTeams.Hyennes){
                    hyennes=true;
                }
                if(camps==RLTeams.Duo_Rani_Kion){
                    Duo_Rani_Kion=true;
                }
                if(camps==RLTeams.Scar){
                    scar=true;
                }
                if(camps==RLTeams.Rejected){
                    rejected=true;
                }
                if(camps==RLTeams.Solo){
                    neutral=true;
                }
            }
            if(rlcamp&&!hyennes&&!Duo_Rani_Kion&&!scar&&!rejected&&!neutral){
                scoreboardmanager.titlemessage("Victoire de la "+RLTeams.LionEarth.getColoredName());
                Bukkit.broadcastMessage("   Victoire de la "+RLTeams.LionEarth.getColoredName());
                return true;
            }
            if(!rlcamp&&hyennes&&!Duo_Rani_Kion&&!scar&&!rejected&&!neutral){
                scoreboardmanager.titlemessage("Victoire: "+RLTeams.Hyennes.getColoredName());
                Bukkit.broadcastMessage("   Victoire: "+RLTeams.Hyennes.getColoredName());
                return true;
            }
            if(!rlcamp&&!hyennes&&Duo_Rani_Kion&&!scar&&!rejected&&!neutral){
                scoreboardmanager.titlemessage("Victoire du "+RLTeams.Duo_Rani_Kion.getColoredName());
                Bukkit.broadcastMessage("   Victoire du "+RLTeams.Duo_Rani_Kion.getColoredName());
                return true;
            }
            if(!rlcamp&&!hyennes&&!Duo_Rani_Kion&&scar&&!rejected&&!neutral){
                scoreboardmanager.titlemessage("Victoire du "+RLTeams.Scar.getColoredName());
                Bukkit.broadcastMessage("   Victoire de "+RLTeams.Scar.getColoredName());
                return true;
            }
            if(!rlcamp&&!hyennes&&!Duo_Rani_Kion&&!scar&&rejected&&!neutral){
                scoreboardmanager.titlemessage("Victoire du "+RLTeams.Rejected.getColoredName());
                Bukkit.broadcastMessage("   Victoire de "+RLTeams.Rejected.getColoredName());
                return true;
            }
            if(!rlcamp&&!hyennes&&!Duo_Rani_Kion&&!scar&&!rejected&&neutral){
                if(RoleHandler.getRoleList().size()==1){
                    Role role = (Role) RoleHandler.getRoleList().values().toArray()[0];
                    scoreboardmanager.titlemessage("Victoire de §f"+role.getName());
                    Bukkit.broadcastMessage("   Victoire de §f"+role.getName());
                    return true;
                }else{
                    return false;
                }

            }
        }
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
                            if (RoiLionUHC.api.getGameProvider().getPlayerList().contains(other.getUniqueId()) && RoiLionUHC.api.getRoleProvider().getRoleOf(other).getCamp() != RLTeams.Hyennes) {
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
                return "tp";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                if (role instanceof Banzai) {
                    Banzai banzai = (Banzai) role;
                    int[][] array = new int[][]{{10, 2, 20}, {36, 2, 27}, {6, 2, 40}, {34, 2, 4}, {54, 9, 27}, {37, 8, 42}};
                    Random random = new Random(System.currentTimeMillis());
                    if (!banzai.used) {
                        ArrayList<UUID> tpedplayers = new ArrayList<>();
                        ArrayList<Player> totp = new ArrayList<>();
                        for (final Player other : Bukkit.getOnlinePlayers()) {
                            if (RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(other.getUniqueId())) {
                                if (other.getLocation().distance(player.getLocation()) < 50) {
                                    totp.add(other);
                                    tpedplayers.add(other.getUniqueId());
                                }
                            }
                        }
                        Structure structure = getStructure().get(0);
                        ArrayList<UUID> uuidArrayList = new ArrayList<>();
                        for(Player player1:totp){
                            uuidArrayList.add(player1.getUniqueId());
                        }
                        NoDamage noDamage = new NoDamage(3,uuidArrayList);
                        noDamage.runTaskTimer(RoiLionUHC.INSTANCE,0,20L);
                        for (Player p : totp) {
                            p.teleport(structure.getPositionRelativeToLocation(array[random.nextInt(array.length)]));
                        }
                        new BukkitRunnable() {
                            int cage_duration = 90;

                            @Override
                            public void run() {
                                cage_duration--;
                                if (cage_duration <= 0) {
                                    ArrayList<UUID> to_remove = new ArrayList<>();
                                    for (UUID UUID : tpedplayers) {
                                        Player tped = Bukkit.getPlayer(UUID);
                                        if (tped != null && tped.isOnline()) {
                                            to_remove.add(UUID);
                                            RoiLionUHC.api.getMapProvider().getMap().PlayerRandomTPMap(tped, 10);
                                        }
                                    }
                                    tpedplayers.removeAll(to_remove);
                                } else {
                                    for (UUID UUID : tpedplayers) {
                                        Player tped = Bukkit.getPlayer(UUID);
                                        if (tped != null && tped.isOnline()) {
                                            RoiLionUHC.api.getPlayerHelper().sendActionBarMessage(tped, "§cTemps restant: " + TimeUtility.transform(cage_duration, "§6"));
                                        }
                                    }
                                }
                                if (tpedplayers.size() == 0 ||RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate()== Gstate.Waiting) {
                                    cancel();
                                }
                            }
                        }.runTaskTimer(RoiLionUHC.INSTANCE, 0, 20L);
                        banzai.used = true;
                    } else {
                        player.sendMessage("§cVous avez déjà utilisé ce pouvoir.");
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable par Banzai uniquement.";
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
                        player.sendMessage("Simba est " + RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Simba.class));
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
                if (RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(player.getUniqueId())) {
                    final Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                    if (role instanceof Kion) {
                        final Kion kion = (Kion) role;
                        if (kion.coolDown.getUtilisation() > 0) {
                            if (kion.coolDown.isAbleToUse()) {
                                kion.coolDown.setUseNow();
                                kion.coolDown.addUtilisation(-1);
                                kion.remaning_time = 15 * 60;
                                player.sendMessage("§7Vous êtes invisible pour les 15 prochaines minutes.");
                            } else player.sendMessage("§7Vous ne pouvez pas utiliser cette capacité.");
                        } else player.sendMessage("§7Vous avez atteint le maximum d'utilisation de cette capacité.");
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Kion";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "infect";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                Role role = RoleHandler.getRoleOf(player);
                if (role instanceof Vitani) {
                    Vitani vitani = (Vitani) role;
                    if (vitani.target == null) {
                        if (strings.length > 0) {
                            String args = strings[0];
                            Player target = Bukkit.getPlayer(args);
                            if (target == null || !RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(target.getUniqueId())) {
                                player.sendMessage("§cCe joueur n'est pas en ligne ou déjà mort.");
                                return true;
                            }
                            if (target.getUniqueId() == player.getUniqueId()) {
                                player.sendMessage("§cVous ne pouvez pas être la cible de cette commande.");
                                return true;
                            }
                            vitani.target = target.getUniqueId();
                        } else {
                            player.sendMessage("§cUsage: /rlu infect <Joueur>");
                        }
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Vitani";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "coco";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(player);
                if (role instanceof Rafiki) {
                    Rafiki rafiki = (Rafiki) role;
                    new CocoGUI(player,rafiki).open();
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Rafiki";
            }
        }, new SubCommand() {
            @Override
            public String subCommand() {
                return "choix";
            }

            @Override
            public boolean execute(Player player, String[] strings) {
                Role role =RoleHandler.getRoleOf(player);
                if(role instanceof Rafiki){
                    Rafiki rafiki = (Rafiki) role;
                    if (strings.length > 0) {
                        String args = strings[0];
                        Player target = Bukkit.getPlayer(args);
                        if (target == null || !RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(target.getUniqueId())) {
                            player.sendMessage("§cCe joueur n'est pas en ligne ou déjà mort.");
                            return true;
                        }
                        if (target.getUniqueId() == player.getUniqueId()) {
                            player.sendMessage("§cVous ne pouvez pas être la cible de cette commande.");
                            return true;
                        }
                        player.sendMessage("§aVotre nouvelle cible est §6"+target.getName());
                        rafiki.targeted=target.getUniqueId();
                    }
                }
                return true;
            }

            @Override
            public String subCommandDescription() {
                return "Utilisable uniquement par Rafiki";
            }
        });
        return command;
    }
}
