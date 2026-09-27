# Changelog

## 1.2.0 — honest colors

Changed:
- Red now means "you can interact": boats, minecarts and every other non-living entity turn red when targeted within interaction range, not just living ones
- Hit through i-frames? The hitbox stays white instead of flashing purple — red only shows when the hit will actually land
- The look direction arrow is on by default for new and upgraded configs
- Only TrueWulf is listed as the author; Sootysplash moved to credits for the original Combat Hitboxes

Removed:
- The purple i-frames color and its settings — one less thing to configure

## 1.1.3 — color rework

Changed:
- Hitbox colors are now simple and clear: red when you can hit the entity, white when you can't — like normal hitboxes
- The target color is red, same as the original Combat Hitboxes
- Wind charges, arrows, snowballs and every other projectile show a plain white hitbox; only ender pearls keep owner colors (blue yours, red enemy)
- Boats, minecarts, items and every other entity now get a hitbox too
- The look direction arrow is bigger, like the vanilla debug arrow
- Removed the crosshair settings entirely

## 1.1.2 — compared to 1.0.3

New:
- Shield color: the hitbox of a blocking player stays fully visible and turns purple; it never disappears
- I-frames color: purple hitbox while the target is invulnerable
- Projectile hitboxes: ender pearls with owner colors, plus hitboxes for every other projectile
- Weapon filter restored and expanded from 1.0.3: bow, crossbow, ender pearl, wind charge, golden apple, golden carrot, XP bottle, totem, firework, splash potion, elytra, end crystal, respawn anchor, glowstone, cobweb, fishing rod, steak, flint and steel, armor stand, lava bucket, water bucket, obsidian — plus shovels and modded melee weapons
- Out-of-range dimming with configurable alpha

Changed:
- Config menu reorganized into 4 tabs: General, Colors & Lines, Projectiles, Weapon Filter
- A separate jar for every Minecraft version: 1.21, 1.21.1 … 1.21.11, 26.1 … 26.3
- Config translated into 8 languages: English, Russian, Ukrainian, Polish, German, Spanish, French, Brazilian Portuguese

Fixed:
- Look direction renders as a real thin line from the eyes (or an arrow) instead of a cube outline
- Pearl hitbox no longer lags behind the pearl
- Arrow hitboxes are visible again
- Old configs from 1.0.3 are migrated automatically, including colors saved without alpha (which rendered as invisible)
- The old orange shield default now migrates to purple
