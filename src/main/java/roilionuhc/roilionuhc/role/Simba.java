package roilionuhc.roilionuhc.role;
import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.Inventory.InventoryUtils;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.PlayerUtils.PlayerUtility;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.CoolDown;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.Triggers.Trigger_OnAnyKill;
import fr.supercomete.head.role.Triggers.Trigger_OnInteractWithUUIDItem;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.nbthandler.NbtTagHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Simba extends Role implements Trigger_WhileAnyTime, Trigger_OnAnyKill,Trigger_OnInteractWithUUIDItem {
    CoolDown coolDown = new CoolDown(0,60*10);
    public Simba(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Simba";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §bvitesse§7 à la mort de Mufasa ainsi que le pseudo de son tueur.",
                "Vous avez un item nommé '§6Rugissement§7' qui vous permet de propulser un joueur à 100blocs de hauteur et 20blocs de longueur."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        ItemStack rugissement = InventoryUtils.getItem(Material.MAGMA_CREAM,"§6Rugissement",null);
        rugissement = NbtTagHandler.createItemStackWithUUIDTag(rugissement,4501);
        return new ItemStack[]{rugissement};
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
    public void OnInteractWithUUIDItem(Player player, int i, PlayerInteractEvent playerInteractEvent) {
        if(i==4501){
            playerInteractEvent.setCancelled(true);
            Player target = PlayerUtility.getTarget(player,20);
            if(!coolDown.isAbleToUse()){
                player.sendMessage("Il vous reste encore "+ TimeUtility.transform(coolDown.getRemainingTime(),"§c"));
                return;
            }
            if(target==null){
                player.sendMessage("§cLa cible n'est pas indiquée correctement.");
                return;
            }
            float angle = player.getLocation().getYaw();
            angle = (angle/360.0F) * 2F *(float)Math.PI;
            double x = 20F * Math.cos(angle);
            double z = 20F * Math.sin(angle);
            final Vector vector = new Vector(x,100,z);
            target.getLocation().add(0,1,0);
            target.setVelocity(vector);
            coolDown.setUseNow();
            player.sendMessage("§7Vous avez rugis sur §6"+target.getName());
            Role targetrole = RoleHandler.getRoleOf(target);
            targetrole.addBonus(new Bonus(BonusType.NoFall,1));
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(target.isOnGround()){
                       targetrole.addBonus(new Bonus(BonusType.NoFall,-1));
                       cancel();
                    }
                }
            }.runTaskTimer(RoiLionUHC.INSTANCE,0,1);
        }
    }

    @Override
    public void onOtherKill(Player player, Player player1) {
        Role role = RoleHandler.getRoleOf(player);
        if(role instanceof Mufasa){
            Player ths = Bukkit.getPlayer(getOwner());
            ths.sendMessage("Mufasa est mort. Son tueur est "+player.getName());
        }
    }

    @Override
    public void WhileAnyTime(Player player) {
        UUID mufasa = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Mufasa.class);
        if(mufasa==null){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,0,20*3));
        }
    }
}