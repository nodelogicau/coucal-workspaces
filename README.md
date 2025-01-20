# Coucal Workspaces

Plan, track and record anything and everything

## Introduction

Coucal Workspaces is the ultimate tool for planning, tracking and linking everything in your life,
whether it be at work, home or anywhere in between.

Use Coucal to create events, actions, notes, and much more. The implicit ability to establish links
between related entities and activities makes the tool even more powerful the more you add to it.

## Collections

TBD.

## Entities

Entities, such as people, places, things, etc., are automatically generated and stored from your events and
other data. These entities are used for auto-complete fields and provide the basis for linking related
data.

The type of entities produced is dependent on the strategy employed. For example, the invitation list for
a meeting (i.e. email addresses) will result in one or more entities of kind individual. Whereas for the
same meeting a populated location will produce an entity of location kind. Similarly, meeting resources
such as applications or devices will also use the corresponding kind for linked entities.

## Filters

Filters provide the ability to focus on a specific subset of collection data. They are easily configurable
using a custom query language that supports advanced property matching.

For example, to filter on all incomplete actions in a collection you can use:

    concept like "semcal:concept:action" and status in ["in_process", "not_started"]

For meetings to be held in the next week:

    concept = "semcal:concept:event:meeting" and dtstart > now() and dtstart < now(1w)



## Quick Add

You can quickly add content using common fields as follows:

| Concept      | Summary   | Start Date | End Date      | Duration      | Due      | Repeats   
|--------------|-----------|------------|---------------|---------------|----------|-----------|
| Event        | Mandatory | Mandatory  | Mandatory (*) | Mandatory (*) | N/A      | Optional  |
| Observance   | Mandatory | Mandatory  | N/A           | N/A           | N/A      | Mandatory |
| Action       | Mandatory | Optional   | Optional      | Optional      | Optional | Optional  |
| Issue        | Mandatory | N/A        | N/A           | N/A           | Optional | N/A       |
| Request      | Mandatory | N/A        | N/A           | N/A           | Optional | Optional  |
| Note         | Mandatory | N/A        | N/A           | N/A           | N/A      | N/A       |
| Report       | Mandatory | N/A        | N/A           | N/A           | N/A      | Mandatory |
| Availability | Mandatory | Optional   | Optional      | Optional      | N/A      | Optional  |
