SELECT id, name, coins, quest_type, questid, date
  FROM public.completed_quests
INNER JOIN quest
  ON completed_quests.questid = quest.id;
