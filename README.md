# HealthBarMod

A simple Minecraft mod that displays customizable health bars around player.

![showcase](./img/showcase1.png)

## Features

* Show health bars for other players (optionally yourself).
* Hide health bars when sneaking.
* Adjustable render distance, size, scale, rotation, and offset.
* Dynamic color: green → yellow → red.
* Render player health text in their team color.

## Commands

`/healthbar <option>` — in-game control.

* `toggle` — Enable/disable health bars
* `self` — Show/hide your own health bar
* `sneak` — Show/Hide when sneaking
* `face` — Whether to keep bar always facing you
* `team` — Whether to render text with team color
* `shadow` — Whether to render text shadow
* `distance <value>` — Set max render distance
* `scale <value>` — Adjust overall scale
* `xoffset <value>` / `yoffset <value>` / `zoffset <value>` — Adjust position
* `barwidth <value>` — Set width
* `barheight <value>` — Set height
* `barmargin <value>` — Space between bar and name
* `barrotation <value>` — Set rotation
* `barxoffset <value>` / `baryoffset <value>` / `barzoffset <value>` — Adjust bar position offset
* `reload` — Reload configuration

## Configuration

All options can be changed in `HealthBarMod.cfg` or via commands.

* `enabled`, `showSelf`, `hideWhenSneaking`, `facePlayer`, `teamColor`, `textShadow` — booleans
* `maxDistance`, `scale`, `xOffset`, `yOffset`, `zOffset`, `barWidth`, `barHeight`, `barMargin`, `barRotation`, `barXOffset`, `barYOffset`, `barZOffset` — numbers

## Notes

[USE THIS MOD AT YOUR OWN RISK](https://support.hypixel.net/hc/en-us/articles/6472550754962-Hypixel-Allowed-Modifications)