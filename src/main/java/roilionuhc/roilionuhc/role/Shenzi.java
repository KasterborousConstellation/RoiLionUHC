package roilionuhc.roilionuhc.role;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.Triggers.Trigger_OnAnyKill;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_WhileNight;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
public class Shenzi extends Role implements HasAdditionalInfo, Trigger_WhileAnyTime, Trigger_WhileNight, Trigger_OnAnyKill {
    boolean resis=false;
    public Shenzi(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Shenzi";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Hyennes;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §cforce§7 pendant la nuit.",
                "Quand vous êtes a plus de 15blocs de Ed, vous obtenez l'effet §bvitesse§7 1.",
                "Si Ed meurt, vous avez l'effet §bvitesse§7 permanent.",
                "Si Banzai vient a mourir dans un rayon de 150blocs autour de vous gagnez l'effet résistance 1 permanent."
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
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez l'identité de Banzai et Ed: "+ RoleHandler.FormalizedGetWhoHaveRole(Banzai.class)+","+" "+RoleHandler.FormalizedGetWhoHaveRole(Ed.class)};
    }

    @Override
    public void WhileAnyTime(Player player){
        if(resis){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*5));
        }
        UUID UUID = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Ed.class);
        if(UUID ==null) {
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,0,20*3));
        }else{
            Player ed = Bukkit.getPlayer(UUID);
            if(ed!=null && ed.getWorld().equals(player.getWorld())){
                if(ed.getLocation().distance(player.getLocation())>15){
                    RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,0,20*3));
                }
            }
        }
    }
    @Override
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*3));
    }

    @Override
    public void onOtherKill(Player player, Player player1) {
        Role role =RoiLionUHC.api.getRoleProvider().getRoleOf(player);
        if(role instanceof Banzai){
            Player owner = Bukkit.getPlayer(getOwner());
            if(owner!=null &&owner.isOnline()&&RoiLionUHC.api.getCoordinateHelper().Distance(player,owner)<150){
                resis=true;
            }
        }
    }
}