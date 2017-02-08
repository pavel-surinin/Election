function getCandidates(self,district) {
  axios
    .get('candidate/' + district + '/district')
    .then(function(response){
      self.setState({
        candidatesList :  response.data,
      });
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.party', err);
    });
}
function getDistrict(self,id) {
  axios
    .get('/district/' + id)
    .then(function(response){
      self.setState({
        districtInfo :  response.data,
        isLoading : false,
      });
      if (response.data.resultSingleRegistered) {
        self.setState({isVotesRegistered : true});
      }
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.district', err);
    });
}
function isCountEqualsOrLess(list,max){
  var count = 0;
  for (var key1 in list) {
    if (list.hasOwnProperty(key1)) {
      count = count + parseInt(list[key1]);
    }
  }
  if (count > max) {
    return false;
  } else {
    return true;
  }
}
var list = {};
var postArray = [];
var errorMesages = [];
var SingleResultContainer = React.createClass({
  getInitialState: function() {
    return {
      districtId : 5,
      candidatesList : [],
      districtInfo : [],
      isLoading : true,
      isVotesRegistered : false,
      errorMesages : [],
    };
  },
  componentWillMount: function() {
    getCandidates(this,this.state.districtId);
    getDistrict(this,this.state.districtId);
  },
  registerVotes :function(id,votes){
    list[id]=votes;
  },
  onHandleSpoiledChange : function(event){
    list[-1991]=event.target.value;
  },
  onHandleSubmit : function(event) {
    postArray = [];
    event.preventDefault();
    errorMesages = [];
    this.setState({errorMesages : []});
    if (!isCountEqualsOrLess(list,this.state.districtInfo.numberOfElectors)) {
      errorMesages.push('Apygarda ' + this.state.districtInfo.name + ' turi tik ' +  this.state.districtInfo.numberOfElectors + ' balsų.');
      this.setState({errorMesages : errorMesages});
    } else {
      for (var key in list) {
        if (list.hasOwnProperty(key)) {
          var self = this;
          postArray.push(
            {
              'candidate' : {'id' : key},
              'district' : {'id' : this.state.districtId},
              'votes' : list[key],
              'datePublished' : new Date(),
            }
          );
        }
      }
      axios
      .post('/result-single', postArray)
      .then(function(response){
        self.setState({isVotesRegistered : true});
      })
      .catch(function(err){
        console.error('SingleResultContainer.onHandleSubmit.axios',err);
      });
    }
  },
  render: function() {
    if (this.state.isLoading) {
      return <div><img src='/images/loading.gif'/></div>;
    }
    if (this.state.isVotesRegistered) {
      return(
        <div>
          {alerts.showSucces('Jūsų apylinkės balsai užregistruoti')}
        </div>
      );
    } else {
      return (
        <SingleResultComponent
          list={this.state.candidatesList}
          registerVotes={this.registerVotes}
          onHandleSpoiledChange={this.onHandleSpoiledChange}
          onHandleSubmit={this.onHandleSubmit}
          errorMesages={this.state.errorMesages}
        />
      );
    }
  }
});


window.SingleResultContainer = SingleResultContainer;
