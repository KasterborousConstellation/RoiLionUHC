package roilionuhc.roilionuhc.role;

import fr.supercomete.enums.Gstate;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.BonusHeart;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_OnKill;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Scar extends Role implements PreAnnouncementExecute, BonusHeart, Trigger_WhileAnyTime, Trigger_OnKill {
    Gstate killedstate_Mufasa= Gstate.Waiting;
    Gstate killedstate_Simba= Gstate.Waiting;
    public Scar(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Scar";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Solo;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez deux coeurs permanent en plus ainsi que faiblesse permanent.",
                "A la mort de Mufasa vous n'avez plus l'effet faiblesse",
                "Si vous tuez Mufasa de votre main, alors vous obtiendrez l'effet force, soit pendant le jour ou pendant la nuit selon le moment ou vous avez tué Mufasa.",
                "Pour vous aider dans cette tache, vous avez Hunger a moins de 5blocs de Mufasa.",
                "A chaque kill que vous faite vous obtenez 1/2 ♥ supplémenataire (20♥ max)",
                "A la mort de Simba vous obtenez une fleche qui pointe vers Mufasa pendant 5min."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return true;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void PreAnnouncement() {
        setCamp(RLTeams.Scar);
    }

    @Override
    public int getHPBonus() {
        return 4;
    }

    @Override
    public void WhileAnyTime(Player player) {
        UUID mufasa = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Mufasa.class);
        UUID simba = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Simba.class);
        if(mufasa!=null) {
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player, new KTBSEffect(PotionEffectType.WEAKNESS, 0, 20 * 4));
        }
        if(killedstate_Simba!=Gstate.Waiting){
            if(RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate().equals(killedstate_Simba)){
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player, new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 20 * 4));
            }
        }
        if(killedstate_Mufasa!=Gstate.Waiting){
            if(RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate().equals(killedstate_Mufasa)){
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player, new KTBSEffect(PotionEffectType.INCREASE_DAMAGE, 0, 20 * 4));
            }
        }
        if(mufasa!=null){
            Player mufasa_player = Bukkit.getPlayer(mufasa);
            if(mufasa_player!=null){
                double distance = RoiLionUHC.api.getCoordinateHelper().Distance(player,mufasa_player);
                if(distance<8){
                    RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.HUNGER,0,20*3));
                }
            }
        }
        if(simba==null){
            if(mufasa!=null){
                Player musafa_player = Bukkit.getPlayer(mufasa);
                if(musafa_player!=null&&musafa_player.isOnline()){
                    String direction = RoiLionUHC.api.getCoordinateHelper().getDirectionArrow(player.getLocation(),musafa_player.getLocation());
                    RoiLionUHC.api.getPlayerHelper().sendActionBarMessage(player,"§cMufasa: "+direction+" "+RoiLionUHC.api.getCoordinateHelper().Distance(player,musafa_player));
                }
            }
        }
    }

    @Override
    public void onKill(Player player, Player killed) {
        if(player.getMaxHealth()<40){
            addBonus(new Bonus(BonusType.Heart,1));
        }
        Gstate state = RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate();
        String add=state==Gstate.Day?"§6le jour":"§bla nuit";
        Role role = RoleHandler.getRoleOf(killed);
        if(role instanceof Mufasa){
            player.sendMessage("Vous avez tué Mufasa, vous obtenez maintenant l'effet force pendant "+add);
            killedstate_Mufasa=state;
        }
        if(role instanceof Simba){
            player.sendMessage("Vous avez tué Mufasa, vous obtenez maintenant l'effet force pendant "+add);
            killedstate_Simba=state;
        }
    }
}