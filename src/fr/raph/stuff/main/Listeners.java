package fr.raph.stuff.main;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Listeners implements Listener, CommandExecutor {
	
	private Main plugin;
	
	private String prefixErreur = ChatColor.DARK_RED + "GiveStuffErreur : ";
	private String prefixStuff = ChatColor.DARK_RED + "GiveStuff : ";
	
	public Listeners(Main main) {
		this.plugin = main;
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity().getPlayer();
		setInv(p);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {	
			
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("giveStuff")) {
				
				if(!p.hasPermission("stuff.giveStuff")) {
					p.sendMessage(prefixErreur + ChatColor.RED + "Vous n'avez pas les permissions nécessaires pour exécuter cette commande !");
					return false;
				}
				
				if(args.length == 0) {
					p.sendMessage(prefixErreur + ChatColor.RED + "La Commande est" + ChatColor.AQUA + " /giveStuff <Nom du joueur>");
					return false;
				}
				
				try {
					Bukkit.getPlayerExact(args[0].toString()).isOnline();
				}catch (Exception e) {
					p.sendMessage(prefixErreur + ChatColor.RED + "Le joueur " + ChatColor.AQUA + args[0].toString() + ChatColor.RED + " n'est pas connecté ou n'existe pas !");
					return false;
				}
				
				Player player = Bukkit.getPlayerExact(args[0].toString());

				getInv(p, player);

			}

		}
		return false;
	}
	
	
	void setInv(Player p) {
		for(int i=0; i < 41 ; i++) {
			plugin.getDConfig().set("StuffDeath." + p.getUniqueId().toString() +".Stuff" + i, p.getInventory().getItem(i));
			try {
				
				plugin.getDConfig().save(plugin.dFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	void getInv(Player p, Player player) {
		
		for(int i = 0 ; i < 41 ; i++) {
			ItemStack it = plugin.getDConfig().getItemStack("StuffDeath." + player.getUniqueId().toString() + ".Stuff" + i);
			player.getInventory().setItem(i, it);
		}
		
		p.sendMessage(prefixStuff + ChatColor.AQUA + "Le stuff de " + ChatColor.RED + player.getName() + ChatColor.AQUA + " a bien été redonné !");
		player.sendMessage(prefixStuff + ChatColor.AQUA + "Votre stuff a bien été redonné par " + ChatColor.RED + p.getName() + ChatColor.AQUA + " !");
		
	}


}
