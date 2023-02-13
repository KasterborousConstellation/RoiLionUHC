package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_OnAnyKill;
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

public class Kovu extends Role implements Trigger_WhileNight, PreAnnouncementExecute,Trigger_OnAnyKill {
    String owner_;
    public Kovu(UUID owner) {
        super(owner);
        owner_=Bukkit.getPlayer(getOwner())!=null?Bukkit.getPlayer(getOwner()).getName():" ";
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Kovu";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Solo;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §cforce§7 pendant la nuit.",
                "Si vous gagnez avec La Terre des Lions ou avec Scar vous avez l'effet résistance de nuit",
                "Si Vitani meurt vous passez dans le Camp de la Terre des lions, seulement si Kiara est en vie. Sinon vous passez dans le Camp de Scar. Si Vitani meurt alors que Kiara et Scar sont mort vous ne changez pas de camp.",
                "Si vous gagné avec Scar et qu'il vient a mourir, vous gagnez 2♥ supplémentaire."
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
        setCamp(RLTeams.Rejected);
    }

    @Override
    public void onOtherKill(Player killed, Player killer) {
        UUID kiara= RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Kiara.class);
        UUID scar= RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Scar.class);
        Player player = Bukkit.getPlayer(getOwner());
        Role role = RoleHandler.getRoleOf(killed);
        if(role instanceof Vitani){
            if(RLTeams.Rejected==getCamp()){
                if(kiara==null){
                    if(scar!=null){
                        if(player!=null && player.isOnline()){
                            player.sendMessage("§cVitani est morte, vous devez donc gagner avec §6Scar. Scar est: "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Scar.class));
                        }
                        this.setCamp(RLTeams.Scar);
                    }
                }else{
                    if(player!=null && player.isOnline()){
                        player.sendMessage("§cVitani est morte, vous devez donc gagner avec §aLa Terre des Lions avec Kiara.");
                    }
                    this.setCamp(RLTeams.LionEarth);
                    for(final Role r : RoleHandler.getRoleList().values()){
                        if(r instanceof Kiara){
                            ((Kiara)r).kovu=owner_;
                            Player player_kiara = Bukkit.getPlayer(r.getOwner());
                            player_kiara.sendMessage("§aKovu est passé dans votre Camp. L'identité de Kovu est "+owner_);
                        }
                    }
                }
            }
        }else if(role instanceof Kiara){
            if(RLTeams.LionEarth==getCamp()){
                if(scar!=null){
                    if(player!=null && player.isOnline()){
                        player.sendMessage("§cKiara est morte, vous devez donc gagner avec §aLa Terre des Lions avec Kiara.");
                    }
                    this.setCamp(RLTeams.Scar);
                }
            }
        }else if(role instanceof Scar){
            if(RLTeams.Scar==getCamp()){
                addBonus(new Bonus(BonusType.Heart,4));
                if(player!=null && player.isOnline()){
                    player.sendMessage("§cScar est mort, vous devez gagner seul, vous gagnez 2♥ permanent supplémenataire.");
                    setCamp(RLTeams.Solo);
                }
            }
        }
    }

    @Override
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,60));
        if(getCamp()==RLTeams.LionEarth||getCamp()==RLTeams.Scar||RLTeams.Solo==getCamp()){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,60));
        }
    }
}
