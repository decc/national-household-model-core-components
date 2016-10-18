(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy name: draughtproofing

                  (target exposure: (schedule.on-group-entry)
                          name: draught-proofing
                          action: (measure.install-draught-proofing proportion: 1)
                          group: (group.all)
                  )))